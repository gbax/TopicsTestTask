package com.gbax.TopicsTestTask.service;

import argo.format.CompactJsonFormatter;
import argo.jdom.JsonArrayNodeBuilder;
import argo.jdom.JsonObjectNodeBuilder;
import com.gbax.TopicsTestTask.dao.MessageDao;
import com.gbax.TopicsTestTask.dao.entity.Message;
import com.gbax.TopicsTestTask.dao.entity.Topic;
import com.gbax.TopicsTestTask.dao.entity.User;
import com.gbax.TopicsTestTask.enums.Errors;
import com.gbax.TopicsTestTask.system.exception.EntityNotFoundException;
import com.gbax.TopicsTestTask.system.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

import static argo.jdom.JsonNodeBuilders.*;

@Service
public class MessageService {

    @Qualifier("messageDao")
    @Autowired
    MessageDao messageDao;

    @Qualifier("topicService")
    @Autowired
    TopicService topicService;

    @Autowired
    SecurityService securityService;

    public List<Message> getMessagesById(Integer id, Integer perPage, Integer page, String order, String sort) throws EntityNotFoundException {
        Topic topic = topicService.getTopicById(id);
        if (topic == null) {
            throw new EntityNotFoundException(Errors.TOPIC_NOT_FOUND);
        }
        return messageDao.getMessagesByTopic(topic, perPage, page, order, sort);
    }

    public List<Message> getMessagesById(Integer id) throws EntityNotFoundException {
        Topic topic = topicService.getTopicById(id);
        if (topic == null) throw new EntityNotFoundException(Errors.TOPIC_NOT_FOUND);
        return messageDao.getMessagesByTopic(topic, null, null, null, null);
    }

    public Message addMessageToTopic(Integer id, Message message) throws EntityNotFoundException {
        return messageDao.addMessage(id, message);
    }

    public void remove(Integer id) throws EntityNotFoundException {
        messageDao.remove(id);
    }

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
     * Для первоначального заполнения
     * @param message
     * @return
     */
    public Message addMessage(Message message) {
        return messageDao.addMessage(message);
    }
}
