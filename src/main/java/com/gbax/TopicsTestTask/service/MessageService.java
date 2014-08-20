package com.gbax.TopicsTestTask.service;

import argo.format.CompactJsonFormatter;
import argo.jdom.JsonArrayNodeBuilder;
import argo.jdom.JsonObjectNodeBuilder;
import com.gbax.TopicsTestTask.dao.MessageDao;
import com.gbax.TopicsTestTask.dao.entity.Message;
import com.gbax.TopicsTestTask.dao.entity.Topic;
import com.gbax.TopicsTestTask.system.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static argo.jdom.JsonNodeBuilders.*;

@Service
public class MessageService {

    @Autowired
    MessageDao messageDao;

    @Autowired
    TopicService topicService;

    @Autowired
    SecurityService securityService;

    public void addMessage(Message message) {
        messageDao.addMessage(message);
    }

    public List getMessagesByTopic(Topic topic) {
        return new ArrayList();//messageDao.getMessagesByTopic(topic);
    }

    public List<Message> getMessagesById(Integer id, Integer perPage, Integer page) {
        Topic topic = topicService.getTopicById(id);
        return messageDao.getMessagesByTopic(topic, perPage, page);
    }

    public Message addMessageToTopic(Integer id, Message message) {
        Topic topic = topicService.getTopicById(id);
        message.setTopic(topic);
        message.setUser(securityService.getSecurityPrincipal());
        return messageDao.addMessage(message);
    }

    public void remove(Integer id) {
        Message message = messageDao.getMessagesById(id);
        messageDao.remove(message);
    }

    public String getMessagesByTopicIdJSON(Integer id, Integer perPage, Integer page) {
        List<Message> messages = getMessagesById(id, null, null);
        int size = messages.size();
        int first = 0;
        if (page != 1) {

                first = (perPage * page) - perPage;

        }


        List<Message> messagesById = getMessagesById(id,perPage, first);

        final JsonObjectNodeBuilder nodeBuilder = anObjectBuilder();



        nodeBuilder.withField("total_page", aNumberBuilder(Integer.toString(size)));

        final JsonArrayNodeBuilder messagesBuider = anArrayBuilder();
        for (Message message : messagesById) {
            final JsonObjectNodeBuilder messageBuilder = anObjectBuilder();
            messageBuilder.withField("id", aNumberBuilder(message.getId().toString()));
            messageBuilder.withField("message", aStringBuilder(message.getMessage()));
            messagesBuider.withElement(messageBuilder);
        }
        nodeBuilder.withField("items", messagesBuider);


        String format = (new CompactJsonFormatter()).format(nodeBuilder.build());
        return format;//.replace("\\\"", "&quot;");
    }
}
