package com.gbax.TopicsTestTask.service;

import com.gbax.TopicsTestTask.dao.MessageDao;
import com.gbax.TopicsTestTask.dao.entity.Message;
import com.gbax.TopicsTestTask.dao.entity.Topic;
import com.gbax.TopicsTestTask.system.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return messageDao.getMessagesByTopic(topic);
    }

    public List<Message> getMessagesById(Integer id) {
        Topic topic = topicService.getTopicById(id);
        return messageDao.getMessagesByTopic(topic);
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
}
