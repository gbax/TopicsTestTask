package com.gbax.TopicsTestTask.dao;

import com.gbax.TopicsTestTask.dao.entity.Topic;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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

    public List<Topic> getTopics() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Topic> query = criteriaBuilder.createQuery(Topic.class);
        Root<Topic> topicRoot = query.from(Topic.class);
        TypedQuery<Topic> query1 = entityManager.createQuery(query);

        return query1.getResultList();
    }
}
