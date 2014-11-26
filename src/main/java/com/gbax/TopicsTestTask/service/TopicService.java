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
import com.gbax.TopicsTestTask.system.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;

import javax.persistence.LockModeType;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static argo.jdom.JsonNodeBuilders.*;


@Service
public class TopicService {

    @Qualifier("topicDao")
    @Autowired
    private TopicDao topicDao;

    @Qualifier("messageService")
    @Autowired
    MessageService messageService;

    @Qualifier("securityService")
    @Autowired
    private SecurityService securityService;

    @Qualifier("userService")
    @Autowired
    UserService userService;

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
            save(topic);
            for (int j = 0;j< 20;j++){
                Message message=new Message();
                message.setMessage(String.format("Test message %s", j));
                message.setTopic(topic);
                message.setUser(user);
                messageService.addMessage(message);
            }
        }
    }

    public Topic addTopic(Topic topic) {
        topic.setUser(securityService.getSecurityPrincipal());
        return topicDao.addTopic(topic);
    }

    public Topic save(Topic topic) {
        return topicDao.addTopic(topic);
    }

    public Topic getTopicById(Integer id) throws EntityNotFoundException {
        Topic topicById = topicDao.getTopicById(id);
        if (topicById == null) throw new EntityNotFoundException(Errors.TOPIC_NOT_FOUND);
        return topicById;
    }

    public List<Topic> getTopics(Integer perPage, Integer first, String order, String sort) {
        return topicDao.getTopics(perPage, first, order, sort);
    }

    public List<Topic> getTopics() {
        return topicDao.getTopics(null, null, null, null);
    }

    public void remove(Integer id) throws EntityNotFoundException {
        topicDao.remove(id);
    }

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
            topicBuilder.withField("updateDate", aStringBuilder(topic.getUpdateDate()!= null ? new SimpleDateFormat("dd.MM.yyyy hh:mm:ss").format(topic.getUpdateDate()) : ""));
            topicBuilder.withField("canDelete", canDelete ? aTrueBuilder() : aFalseBuilder());
            topicsBuider.withElement(topicBuilder);
        }
        nodeBuilder.withField("items", topicsBuider);
        return new CompactJsonFormatter().format(nodeBuilder.build());
    }

    public Topic merge(Topic topic) {
        topicDao.merge(topic);
        return topic;
    }
}
