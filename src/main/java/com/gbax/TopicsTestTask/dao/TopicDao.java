package com.gbax.TopicsTestTask.dao;

import com.gbax.TopicsTestTask.dao.entity.Topic;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by abayanov
 * Date: 15.08.14
 */
@Repository("topicDao")
@Transactional
public class TopicDao {

    @PersistenceContext
    private EntityManager entityManager;


    public void addTopic(Topic topic) {
        entityManager.persist(topic);
    }

    public Topic getTopicById(Integer id) {
        return entityManager.find(Topic.class, id);
    }

    public List getAllTopics() {
        return entityManager.createQuery("select t from Topic t").getResultList();
    }

}
