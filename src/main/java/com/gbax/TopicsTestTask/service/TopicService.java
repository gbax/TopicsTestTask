package com.gbax.TopicsTestTask.service;

import com.gbax.TopicsTestTask.dao.TopicDao;
import com.gbax.TopicsTestTask.dao.entity.Topic;
import com.gbax.TopicsTestTask.system.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TopicService {

    @Qualifier("topicDao")
    @Autowired
    private TopicDao topicDao;

    @Qualifier("securityService")
    @Autowired
    private SecurityService securityService;

    public Topic addTopic(Topic topic) {
        topic.setUser(securityService.getSecurityPrincipal());
        return topicDao.addTopic(topic);
    }

    public Topic save(Topic topic) {
        return topicDao.addTopic(topic);
    }

    public Topic getTopicById(Integer id) {
        return topicDao.getTopicById(id);
    }

    public List<Topic> getTopics() {
        return topicDao.getTopics();
    }

    public void remove(Integer id) {
        Topic topic = topicDao.getTopicById(id);
        topicDao.remove(topic);
    }
}
