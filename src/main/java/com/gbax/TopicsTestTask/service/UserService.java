package com.gbax.TopicsTestTask.service;

import com.gbax.TopicsTestTask.dao.UserDao;
import com.gbax.TopicsTestTask.dao.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с пользователями
 * Created by abayanov
 * Date: 15.08.14
 */
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    /**
     * Добавление пользователя
     * @param user пользователь
     */
    public void addUser(User user) {
        userDao.addUser(user);
    }

    /**
     * Получение пользователя по имени
     * @param name имя пользователя
     * @return пользователь
     */
    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }

}
