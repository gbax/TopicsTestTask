package com.gbax.TopicsTestTask.dao;

import com.gbax.TopicsTestTask.dao.entity.Message;
import com.gbax.TopicsTestTask.dao.entity.Topic;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository("messageDao")
@Transactional
public class MessageDao {

    @PersistenceContext
    private EntityManager entityManager;


    public void addMessage(Message message) {
        entityManager.persist(message);
    }

    public List<Message> getMessageByTopicId(Integer id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Message> query = criteriaBuilder.createQuery(Message.class);
        Root<Message> topicRoot = query.from(Message.class);
        ParameterExpression<Integer> p = criteriaBuilder.parameter(Integer.class);
        query.select(topicRoot).where(criteriaBuilder.equal(topicRoot.get("topic.id"), p));
        TypedQuery<Message> query1 = entityManager.createQuery(query);
        query1.setParameter(p, id);

        return query1.getResultList();
    }

    public List getMessagesByTopic(Topic topic) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Message> query = criteriaBuilder.createQuery(Message.class);
        Root<Message> topicRoot = query.from(Message.class);
        ParameterExpression<Topic> p = criteriaBuilder.parameter(Topic.class);
        query.select(topicRoot).where(criteriaBuilder.equal(topicRoot.get("topic"), p));
        TypedQuery<Message> query1 = entityManager.createQuery(query);
        query1.setParameter(p, topic);

        return query1.getResultList();
    }

}
