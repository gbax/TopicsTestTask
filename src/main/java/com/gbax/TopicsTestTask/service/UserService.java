package com.gbax.TopicsTestTask.service;

import com.gbax.TopicsTestTask.dao.UserDao;
import com.gbax.TopicsTestTask.dao.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by abayanov
 * Date: 15.08.14
 */

@Service
public class UserService {

    @Qualifier("userDao")
    @Autowired
    UserDao userDao;

    public void addUser(User user) {
        userDao.addUser(user);
    }

    public User getUserById(Integer id) {
        return userDao.getUserById(id);
    }

    public List getAllUser() {
        return userDao.getAllUser();
    }

    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }

}
