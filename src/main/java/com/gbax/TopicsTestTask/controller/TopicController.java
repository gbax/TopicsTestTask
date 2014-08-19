package com.gbax.TopicsTestTask.controller;

import com.gbax.TopicsTestTask.dao.entity.Topic;
import com.gbax.TopicsTestTask.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by abayanov
 * Date: 18.08.14
 */
@Controller
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    TopicService topicService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Topic> topics() {
        return topicService.getTopics();
    }
}
