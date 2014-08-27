package com.gbax.TopicsTestTask.dao;

import com.gbax.TopicsTestTask.dao.entity.Message;
import com.gbax.TopicsTestTask.dao.entity.Topic;
import com.gbax.TopicsTestTask.enums.Errors;
import com.gbax.TopicsTestTask.system.exception.EntityNotFoundException;
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

    @Transactional
    public Message addMessage(Message message) {
        return entityManager.merge(message);
    }

    public Message getMessagesById(Integer id) {
        return entityManager.find(Message.class, id);
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

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void remove(Integer messageId) throws EntityNotFoundException {
        Message message = getMessagesById(messageId);
        if (message == null) {
            throw new EntityNotFoundException(Errors.MESSAGE_NOT_FOUND);
        }
        //entityManager.lock(message, LockModeType.WRITE);
        entityManager.remove(message);
    }
}
