package com.gbax.TopicsTestTask.service;

import argo.format.CompactJsonFormatter;
import argo.jdom.JsonArrayNodeBuilder;
import argo.jdom.JsonObjectNodeBuilder;
import com.gbax.TopicsTestTask.dao.TopicDao;
import com.gbax.TopicsTestTask.dao.entity.Topic;
import com.gbax.TopicsTestTask.dao.entity.User;
import com.gbax.TopicsTestTask.system.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;

import static argo.jdom.JsonNodeBuilders.*;


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

    public List<Topic> getTopics(Integer perPage, Integer first, String order, String sort) {
        return topicDao.getTopics(perPage, first, order, sort);
    }

    public List<Topic> getTopics() {
        return topicDao.getTopics(null,  null,  null,  null);
    }

    @Transactional
    public void remove(Integer id) {
        Topic topic = topicDao.getTopicById(id);
        topicDao.remove(topic);
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
            final JsonObjectNodeBuilder topicBuilder = anObjectBuilder();
            topicBuilder.withField("id", aNumberBuilder(topic.getId().toString()));
            topicBuilder.withField("description", aStringBuilder(topic.getDescription()));
            topicBuilder.withField("updateDate", aStringBuilder(new SimpleDateFormat("dd.MM.yyyy hh:mm:ss").format(topic.getMessage().getDate())));
            topicBuilder.withField("canDelete", user == null ? aFalseBuilder() :
                    topic.getUser().getId().equals(user.getId()) ? aTrueBuilder() : aFalseBuilder());
            topicsBuider.withElement(topicBuilder);
        }
        nodeBuilder.withField("items", topicsBuider);

        return new CompactJsonFormatter().format(nodeBuilder.build());
    }
}
