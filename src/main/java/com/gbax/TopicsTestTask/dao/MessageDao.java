package com.gbax.TopicsTestTask.dao;

import com.gbax.TopicsTestTask.dao.entity.Message;
import com.gbax.TopicsTestTask.dao.entity.Topic;
import com.gbax.TopicsTestTask.enums.Errors;
import com.gbax.TopicsTestTask.service.TopicService;
import com.gbax.TopicsTestTask.system.exception.EntityNotFoundException;
import com.gbax.TopicsTestTask.system.security.SecurityService;
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
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository("messageDao")
public class MessageDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TopicService topicService;

    @Autowired
    SecurityService securityService;

    /**
     * Для первоначального заполнения
     * @param message
     * @return
     */
    @Transactional
    public Message addMessage(Message message) {
        return entityManager.merge(message);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Message addMessage(Integer topicId, Message message) throws EntityNotFoundException {
        Topic topic = entityManager.find(Topic.class, topicId, LockModeType.PESSIMISTIC_READ);
        if (topic == null) throw new EntityNotFoundException(Errors.TOPIC_NOT_FOUND);
        message.setTopic(topic);
        message.setUser(securityService.getSecurityPrincipal());
        Message merge = entityManager.merge(message);
        topic.setUpdateDate(message.getDate());
        topicService.merge(topic);
        return merge;
    }

    @Transactional(readOnly = true)
    public List<Message> getMessagesByTopic(Topic topic, Integer perPage, Integer first, String order, String sort) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Message> query = criteriaBuilder.createQuery(Message.class);
        Root<Message> topicRoot = query.from(Message.class);
        ParameterExpression<Topic> p = criteriaBuilder.parameter(Topic.class);
        query.select(topicRoot).where(criteriaBuilder.equal(topicRoot.get("topic"), p));
        if (sort != null && order != null) {
            query.orderBy(order.equals("asc") ?
                    criteriaBuilder.asc(topicRoot.get(sort)) :
                    criteriaBuilder.desc(topicRoot.get(sort)));
        }
        TypedQuery<Message> query1 = entityManager.createQuery(query);
        query1.setParameter(p, topic);
        if (perPage != null && first != null) {
            query1.setFirstResult(first);
            query1.setMaxResults(perPage);
        }
        return query1.getResultList();
    }

    @Transactional
    public void remove(Integer messageId) throws EntityNotFoundException {
        Message message = entityManager.find(Message.class, messageId, LockModeType.PESSIMISTIC_READ);
        if (message == null) {
            throw new EntityNotFoundException(Errors.MESSAGE_NOT_FOUND);
        }
        entityManager.remove(message);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Message> deleteMessagesByTopic(Topic topic) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Message> query = criteriaBuilder.createQuery(Message.class);
        Root<Message> topicRoot = query.from(Message.class);
        ParameterExpression<Topic> p = criteriaBuilder.parameter(Topic.class);
        query.select(topicRoot).where(criteriaBuilder.equal(topicRoot.get("topic"), p));
        TypedQuery<Message> query1 = entityManager.createQuery(query);
        query1.setParameter(p, topic);
        List<Message> resultList = query1.getResultList();
        for (Message message: resultList) {
            Message message1 = entityManager.find(Message.class, message.getId(), LockModeType.PESSIMISTIC_READ);
            entityManager.remove(message1);
        }
        entityManager.flush();
        return resultList;
    }
}
