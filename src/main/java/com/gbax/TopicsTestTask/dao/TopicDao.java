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


    public Topic addTopic(Topic topic) {
        entityManager.persist(topic);
        return topic;
    }

    public Topic getTopicById(Integer id) {
        Topic topic = entityManager.find(Topic.class, id);
        return topic;
    }

    public List<Topic> getTopics(Integer perPage, Integer first, String order, String sort) {
        if (sort != null && sort.equals("updateDate")) {
            sort = "message.date";
        }
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Topic> query = criteriaBuilder.createQuery(Topic.class);
        Root<Topic> topicRoot = query.from(Topic.class);
        query.select(topicRoot);
        if (sort != null && order != null) {
            query.orderBy(order.equals("asc") ?
                    criteriaBuilder.asc(topicRoot.get(sort)) :
                    criteriaBuilder.desc(topicRoot.get(sort)));
        }
        TypedQuery<Topic> query1 = entityManager.createQuery(query);
        if (perPage != null && first != null) {
            query1.setFirstResult(first);
            query1.setMaxResults(perPage);
        }
        return query1.getResultList();
    }

    public void remove(Topic topic) {
        entityManager.remove(topic);
    }
}
