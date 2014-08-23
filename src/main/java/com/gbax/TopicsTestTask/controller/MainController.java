package com.gbax.TopicsTestTask.controller;

import com.gbax.TopicsTestTask.dao.entity.Message;
import com.gbax.TopicsTestTask.dao.entity.Topic;
import com.gbax.TopicsTestTask.dao.entity.User;
import com.gbax.TopicsTestTask.service.MessageService;
import com.gbax.TopicsTestTask.service.TopicService;
import com.gbax.TopicsTestTask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;


@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @Autowired
    TopicService topicService;

    public void fillData(){
        User user = new User();
        user.setName("1");
        user.setPassword("1");
        userService.addUser(user);

        User user2 = new User();
        user2.setName("2");
        user2.setPassword("2");
        userService.addUser(user2);

        for (int i = 0; i < 20; i++) {
            Topic topic = new Topic();
            topic.setDescription(String.format("Test topic %s", i));
            topic.setUser(user);
            topicService.save(topic);
            for (int j = 0;j< 40;j++){
                Message message=new Message();
                message.setMessage(String.format("Test message %s", j));
                message.setTopic(topic);
                message.setUser(user);
                messageService.addMessage(message);
            }
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "redirect:/index";
    }

    @RequestMapping(value="/loginfailed", method = RequestMethod.GET)
    public String loginError(ModelMap model) {
        model.addAttribute("error", "true");
        return "index";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView showMainForm() {
        ModelAndView model = new ModelAndView("index");
        return model;
    }

    @RequestMapping(value = "/topic/{id}", method = RequestMethod.GET)
    public ModelAndView showTopicForm(@PathVariable("id") Integer id) {
        Topic topic = topicService.getTopicById(id);
        ModelAndView model = new ModelAndView("topic");
        model.addObject("topic", topic);
        return model;
    }
}

