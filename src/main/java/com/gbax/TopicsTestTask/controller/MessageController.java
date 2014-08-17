package com.gbax.TopicsTestTask.controller;


import com.gbax.TopicsTestTask.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MessageController {

    @Autowired
    MessageService messageService;

    @RequestMapping(value = "topic/{id}", method = RequestMethod.GET)
    public ModelAndView showTopicForm(@PathVariable("id") Integer id) {
        ModelAndView model = new ModelAndView("topic");
        return model;
    }

    @RequestMapping(value = "topic/messages/{id}", method = RequestMethod.GET, headers = "Accept=text/plain;Charset=UTF-8")
    @ResponseBody
    public String topics(@PathVariable("id") Integer id) {
        return messageService.getMessagesByTopicIdJSON(id);
    }

}
