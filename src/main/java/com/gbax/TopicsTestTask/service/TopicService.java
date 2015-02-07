package com.gbax.TopicsTestTask.service;

import argo.format.CompactJsonFormatter;
import argo.jdom.JsonArrayNodeBuilder;
import argo.jdom.JsonObjectNodeBuilder;
import com.gbax.TopicsTestTask.dao.TopicDao;
import com.gbax.TopicsTestTask.dao.entity.Message;
import com.gbax.TopicsTestTask.dao.entity.Topic;
import com.gbax.TopicsTestTask.dao.entity.User;
import com.gbax.TopicsTestTask.enums.Errors;
import com.gbax.TopicsTestTask.system.exception.EntityNotFoundException;
import com.gbax.TopicsTestTask.system.security.api.ISecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static argo.jdom.JsonNodeBuilders.*;

/**
 * Сервис для работы с топиками
 */
@Service
public class TopicService {

    @Autowired
    private TopicDao topicDao;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ISecurityService securityService;

    @Autowired
    private UserService userService;

    /**
     * Заполнение БД тестовыми данными
     */
    public void fillDatabase() {
        User user = new User();
        user.setName("1");
        user.setPassword("1");
        userService.addUser(user);

        User user2 = new User();
        user2.setName("2");
        user2.setPassword("2");
        userService.addUser(user2);

        for (int i = 0; i < 20; i++) {
            Topic topic = new Topic();
            topic.setDescription(String.format("Test topic %s", i));
            topic.setUser(user);
            topic.setUpdateDate(new Date());
            topicDao.addTopic(topic);
            for (int j = 0; j < 20; j++) {
                Message message = new Message();
                message.setMessage(String.format("Test message %s", j));
                message.setTopic(topic);
                message.setUser(user);
                messageService.addMessage(message);
            }
        }
    }

    /**
     * Заполнение БД данными для тестов
     */
    public void fillDatabaseForTest() {
        User user = new User();
        user.setName("1");
        user.setPassword("1");
        userService.addUser(user);

        for (int i = 0; i < 2; i++) {
            Topic topic = new Topic();
            topic.setDescription(String.format("Test topic %s", i));
            topic.setUser(user);
            topic.setUpdateDate(new Date());
            topicDao.addTopic(topic);
            for (int j = 0; j < 5; j++) {
                Message message = new Message();
                message.setMessage(String.format("Test message %s", j));
                message.setTopic(topic);
                message.setUser(user);
                messageService.addMessage(message);
            }
        }
    }

    /**
     * Сохранение топика
     *
     * @param topic топик
     * @return сохраненный топик
     */
    public Topic addTopic(Topic topic) {
        topic.setUser(securityService.getSecurityPrincipal());
        return topicDao.addTopic(topic);
    }

    /**
     * Получение списка топиков по ID
     *
     * @param id ID топика
     * @return топик
     * @throws EntityNotFoundException топик не найден
     */
    public Topic getTopicById(Integer id) throws EntityNotFoundException {
        Topic topicById = topicDao.getTopicById(id);
        if (topicById == null) throw new EntityNotFoundException(Errors.TOPIC_NOT_FOUND);
        return topicById;
    }

    /**
     * Получение страницы с топиками
     *
     * @param perPage кол-во сообщения на странице
     * @param first   номер страницы
     * @param order   порядок сортировки
     * @param sort    столбец сортировки
     * @return страница с топиками
     */
    public List<Topic> getTopics(Integer perPage, Integer first, String order, String sort) {
        return topicDao.getTopics(perPage, first, order, sort);
    }

    /**
     * Получение всех топиков
     *
     * @return список топиков
     */
    public List<Topic> getTopics() {
        return topicDao.getTopics(null, null, null, null);
    }

    /**
     * Удаление топика
     *
     * @param id ID топика
     * @throws EntityNotFoundException топик не найден
     */
    public void remove(Integer id) throws EntityNotFoundException {
        topicDao.remove(id);
    }

    /**
     * Получение страницы с топиками в виде JSON
     * (конвертация в json выполняется в сервисе(а не в маппере) по причине того,
     * что компоненту пагинатора на фронтенде требуются дополнительные данные об общем кол-ве страниц)
     * TODO надо исправить это
     *
     * @param perPage кол-во сообщения на странице
     * @param page    номер страницы
     * @param order   порядок сортировки
     * @param sort    столбец сортировки
     * @return страница с топиками в виде JSON
     */
    public String getTopicsJSON(Integer perPage, Integer page, String order, String sort) {
        List<Topic> topics = getTopics();
        int size = topics.size();
        int first = 0;
        if (page != 1) {
            first = (perPage * page) - perPage;
        }
        List<Topic> topicsPaged = getTopics(perPage, first, order, sort);

        final JsonObjectNodeBuilder nodeBuilder = anObjectBuilder();
        nodeBuilder.withField("total_page", aNumberBuilder(Integer.toString(size)));
        User user = securityService.getSecurityPrincipal();
        final JsonArrayNodeBuilder topicsBuider = anArrayBuilder();
        for (Topic topic : topicsPaged) {
            Boolean canDelete = user != null && topic.getUser() != null && topic.getUser().getId().equals(user.getId());
            final JsonObjectNodeBuilder topicBuilder = anObjectBuilder();
            topicBuilder.withField("id", aNumberBuilder(topic.getId().toString()));
            topicBuilder.withField("description", aStringBuilder(topic.getDescription()));
            topicBuilder.withField("updateDate", aStringBuilder(topic.getUpdateDate() != null ? new SimpleDateFormat("dd.MM.yyyy hh:mm:ss").format(topic.getUpdateDate()) : ""));
            topicBuilder.withField("canDelete", canDelete ? aTrueBuilder() : aFalseBuilder());
            topicsBuider.withElement(topicBuilder);
        }
        nodeBuilder.withField("items", topicsBuider);
        return new CompactJsonFormatter().format(nodeBuilder.build());
    }
}
