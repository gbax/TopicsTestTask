package com.gbax.TopicsTestTask.controller;

import com.gbax.TopicsTestTask.dao.entity.Message;
import com.gbax.TopicsTestTask.dao.entity.Topic;
import com.gbax.TopicsTestTask.dao.entity.User;
import com.gbax.TopicsTestTask.service.MessageService;
import com.gbax.TopicsTestTask.service.TopicService;
import com.gbax.TopicsTestTask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
public class MainController {

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @Autowired
    TopicService topicService;

    public void fillData(){
        for (int i = 0; i < 4; i++) {
            Topic topic = new Topic();
            topic.setDescription(String.format("Test topic %s", i));
            topicService.addTopic(topic);
            for (int j = 0;j< 3;j++){
                Message message=new Message();
                message.setMesssage(String.format("Test message %s", j));
                message.setTopic(topic);
                messageService.addMessage(message);
            }
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
        ModelAndView model = new ModelAndView("index");
        return model;
    }

    @RequestMapping(value = "topics", method = RequestMethod.GET, headers = "Accept=text/plain;Charset=UTF-8")
    @ResponseBody
    public String topics() {
        return topicService.getTopicsJSON();
    }

}

