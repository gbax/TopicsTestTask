package com.gbax.TopicsTestTask.controller;

import com.gbax.TopicsTestTask.dao.entity.Message;
import com.gbax.TopicsTestTask.dao.entity.Topic;
import com.gbax.TopicsTestTask.service.MessageService;
import com.gbax.TopicsTestTask.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/topic/messages")
public class MessageController {

    @Autowired
    MessageService messageService;

    @Autowired
    HttpServletRequest request;

    @RequestMapping(value = "{topicId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Message> getMessages(@PathVariable("topicId") Integer topicId) {
        Integer perPage = Integer.parseInt(request.getParameter("per_page"));
        Integer page = Integer.parseInt(request.getParameter("page"));
        String order = request.getParameter("order");
        return messageService.getMessagesById(topicId);
    }

    @RequestMapping(value = "{topicId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Message create(@PathVariable("topicId") Integer topicId,
                          @RequestBody Message message) throws IOException {
       return messageService.addMessageToTopic(topicId, message);
    }

    @RequestMapping(value = "/{topicId}/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable("topicId") Integer topicId, @PathVariable("id") Integer id) {
        messageService.remove(id);
    }
}
