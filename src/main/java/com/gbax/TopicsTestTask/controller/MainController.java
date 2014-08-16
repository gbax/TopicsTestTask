package com.gbax.TopicsTestTask.controller;
import com.gbax.TopicsTestTask.dao.TopicDao;
import com.gbax.TopicsTestTask.dao.entity.Topic;
import com.gbax.TopicsTestTask.dao.entity.User;
import com.gbax.TopicsTestTask.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import org.springframework.transaction.TransactionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import java.util.List;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;


@Controller
public class MainController {

    @Qualifier("topicDao")
    @Autowired
    TopicDao topicDao; //TODO!

    @Autowired
    UserService userService; //TODO!

    public void fillData(){
        for (int i = 0; i < 10; i++) {
            Topic topic = new Topic();
            topic.setDescription("Test");
            topicDao.addTopic(topic);
        }


        User user = new User();
        user.setName("1");
        user.setPassword("1");
        userService.addUser(user);

    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "redirect:/topicList";
    }

    @RequestMapping(value = "topicList", method = RequestMethod.GET)
    public ModelAndView showMainForm() {


        Topic topic = new Topic();
        topic.setDescription("Test");
        topicDao.addTopic(topic);

        Topic newTop = topicDao.getTopicById(1);

        List tps = topicDao.getAllTopics();

        ModelAndView model = new ModelAndView("index");
        model.addObject("message", "Start backbone application");
        return model;
    }

}

