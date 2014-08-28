package com.gbax.TopicsTestTask.dao;

import com.gbax.TopicsTestTask.dao.entity.Topic;
import com.gbax.TopicsTestTask.enums.Errors;
import com.gbax.TopicsTestTask.service.MessageService;
import com.gbax.TopicsTestTask.system.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
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
public class TopicDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Qualifier("messageService")
    @Autowired
    private MessageService messageService;

    @Transactional
    public Topic addTopic(Topic topic) {
        entityManager.persist(topic);
        return topic;
    }

    @Transactional
    public Topic merge(Topic topic) {
        entityManager.merge(topic);
        return topic;
    }

    public Topic getTopicById(Integer id) {
        Topic topic = entityManager.find(Topic.class, id);
        return topic;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Topic getTopicByIdWithLock(Integer id) {
        Topic topic = entityManager.find(Topic.class, id, LockModeType.PESSIMISTIC_READ);
        return topic;
    }

    @Transactional(readOnly = true)
    public List<Topic> getTopics(Integer perPage, Integer first, String order, String sort) {
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

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void remove(Integer topicID) throws EntityNotFoundException {
        Topic topic = entityManager.find(Topic.class, topicID, LockModeType.PESSIMISTIC_READ);
        if (topic == null) {
            throw new EntityNotFoundException(Errors.TOPIC_NOT_FOUND);
        }
        messageService.deleteMessagesByTopic(topic);
        entityManager.remove(topic);

    }
}
