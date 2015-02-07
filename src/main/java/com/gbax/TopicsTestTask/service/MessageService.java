package com.gbax.TopicsTestTask.service;

import argo.format.CompactJsonFormatter;
import argo.jdom.JsonArrayNodeBuilder;
import argo.jdom.JsonObjectNodeBuilder;
import com.gbax.TopicsTestTask.dao.MessageDao;
import com.gbax.TopicsTestTask.dao.entity.Message;
import com.gbax.TopicsTestTask.dao.entity.Topic;
import com.gbax.TopicsTestTask.dao.entity.User;
import com.gbax.TopicsTestTask.enums.Errors;
import com.gbax.TopicsTestTask.system.exception.CantParseStringDateException;
import com.gbax.TopicsTestTask.system.exception.EntityNotFoundException;
import com.gbax.TopicsTestTask.system.security.api.ISecurityService;
import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static argo.jdom.JsonNodeBuilders.*;

/**
 *Сервис для работы с сообщениями топика
 */
@Service
public class MessageService {

    @Autowired
    MessageDao messageDao;

    @Autowired
    TopicService topicService;

    @Autowired
    ISecurityService securityService;

    /**
     * Получение страницы с сообщениями топика
     * @param id ID топика
     * @param perPage кол-во сообщения на странице
     * @param page номер страницы
     * @param order порядок сортировки
     * @param sort столбец сортировки
     * @return страница с сообщениями топика
     * @throws EntityNotFoundException топик не найден
     */
    public List<Message> getMessagesById(Integer id, Integer perPage, Integer page, String order, String sort) throws EntityNotFoundException {
        Topic topic = topicService.getTopicById(id);
        if (topic == null) {
            throw new EntityNotFoundException(Errors.TOPIC_NOT_FOUND);
        }
        return messageDao.getMessagesByTopic(topic, perPage, page, order, sort);
    }

    /**
     * Получение сообщении по ID топика
     * @param topicId ID топика
     * @return список сообщений
     * @throws EntityNotFoundException топик не найден
     */
    public List<Message> getMessagesById(Integer topicId) throws EntityNotFoundException {
        Topic topic = topicService.getTopicById(topicId);
        if (topic == null) throw new EntityNotFoundException(Errors.TOPIC_NOT_FOUND);
        return messageDao.getMessagesByTopic(topic, null, null, null, null);
    }

    /**
     * Добавление сообщения в топик
     * @param topicId ID топика
     * @param message добавляемое сообщение
     * @return добавленное сообщение
     * @throws EntityNotFoundException топик не найден
     */
    public Message addMessageToTopic(Integer topicId, Message message) throws EntityNotFoundException {
        return messageDao.addMessage(topicId, message);
    }

    /**
     * Удаление сообщения из топика
     * @param id ID сообщения
     * @throws EntityNotFoundException сообщение не найдено
     */
    public void remove(Integer id) throws EntityNotFoundException {
        messageDao.remove(id);
    }

    /**
     * Получение сообщении по ID топика в виде JSON
     * (конвертация в json выполняется в сервисе(а не в маппере) по причине того,
     * что компоненту пагинатора на фронтенде требуются дополнительные данные об общем кол-ве страниц)
     * TODO надо исправить это
     * @param id ID топика
     * @param perPage кол-во сообщения на странице
     * @param page номер страницы
     * @param order порядок сортировки
     * @param sort столбец сортировки
     * @return страница с сообщениями топика в виде JSON
     * @throws EntityNotFoundException топик не найден
     */
    public String getMessagesByTopicIdJSON(Integer id, Integer perPage, Integer page, String order, String sort) throws EntityNotFoundException {
        List<Message> messages = getMessagesById(id);
        int size = messages.size();
        int first = 0;
        if (page != 1) {
                first = (perPage * page) - perPage;
        }

        List<Message> messagesById = getMessagesById(id, perPage, first, order, sort);

        final JsonObjectNodeBuilder nodeBuilder = anObjectBuilder();
        nodeBuilder.withField("total_page", aNumberBuilder(Integer.toString(size)));
        User user = securityService.getSecurityPrincipal();

        final JsonArrayNodeBuilder messagesBuider = anArrayBuilder();
        for (Message message : messagesById) {
            Boolean canDelete = user != null && message.getUser() != null && message.getUser().getId().equals(user.getId());
            final JsonObjectNodeBuilder messageBuilder = anObjectBuilder();
            messageBuilder.withField("id", aNumberBuilder(message.getId().toString()));
            messageBuilder.withField("message", aStringBuilder(message.getMessage()));
            messageBuilder.withField("date", aStringBuilder(new SimpleDateFormat("dd.MM.yyyy hh:mm:ss").format(message.getDate())));
            messageBuilder.withField("canDelete", canDelete ? aTrueBuilder() : aFalseBuilder());
            messagesBuider.withElement(messageBuilder);
        }
        nodeBuilder.withField("items", messagesBuider);
        return new CompactJsonFormatter().format(nodeBuilder.build());
    }

    /**
     * Добавление сообщения в топик
     * (Необходимо для первоначального заполнения)
     * @param message сообщение
     * @return сохраненное сообщение
     */
    public Message addMessage(Message message) {
        return messageDao.addMessage(message);
    }

    /**
     * Метод для теста
     * @param dateString дата в строковом виде
     * @return объект Date
     */
    public Date getDateFromString(String dateString) throws CantParseStringDateException {
        if (StringUtils.isNullOrEmpty(dateString)) {
            throw new CantParseStringDateException("Невозможно преобразовать дату, строка не должна быть пустой");
        }
        List<String> formats = Arrays.asList(
                "dd.MM.yyyy",
                "dd:MM:yy",
                "dd-MM-yyyy hh-mm",
                "dd-MM hh-mm",
                "MM-dd-yyyy hh:mm"
        );
        Date date = null;
        for (String format : formats) {
            try {
                date = (new SimpleDateFormat(format)).parse(dateString);
            } catch (ParseException ignored) {}
            if (date != null) {
                break;
            }
        }
        if (date == null) {
            throw new CantParseStringDateException(
                    String.format("Невозможно преобразовать дату %s. Неизвестный формат даты.", dateString));
        }
        return date;
    }
}
