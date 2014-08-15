package com.gbax.TopicsTestTask.controller;
import com.gbax.TopicsTestTask.dao.entity.Topic;
import org.springframework.stereotype.Controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    com.gbax.TopicsTestTask.dao.TopicDao topicDao;

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

