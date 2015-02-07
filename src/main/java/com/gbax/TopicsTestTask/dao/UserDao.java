package com.gbax.TopicsTestTask.dao;

import com.gbax.TopicsTestTask.dao.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
@Transactional
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;


    public void addUser(User user) {
        entityManager.persist(user);
    }

    public User getUserById(Integer id) {
        return entityManager.find(User.class, id);
    }

    public List getAllUser() {
        return entityManager.createQuery("select t from User t").getResultList();
    }

    public User getUserByName(String name) {
        List resultList = entityManager.createQuery("select t from User t where t.name = :name").
                setParameter("name", name).
                getResultList();
        return resultList.size() == 1 ? (User)resultList.get(0) : null;
    }

}
