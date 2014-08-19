package com.gbax.TopicsTestTask.controller;

import com.gbax.TopicsTestTask.dao.entity.Topic;
import com.gbax.TopicsTestTask.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by abayanov
 * Date: 18.08.14
 */
@Controller
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    TopicService topicService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Topic> topics() {
        return topicService.getTopics();
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Topic create(@RequestBody Topic topic) throws IOException {
        return topicService.addTopic(topic);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable("id") Integer id) {
        topicService.remove(id);
    }
}
