package com.gbax.TopicsTestTask.controller;

import com.gbax.TopicsTestTask.dao.entity.Message;
import com.gbax.TopicsTestTask.service.MessageService;
import com.gbax.TopicsTestTask.system.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Controller
@RequestMapping("/topic/messages")
public class MessageController {

    @Autowired
    MessageService messageService;

    @Autowired
    HttpServletRequest request;

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public void handleEntityNotFoundException(final EntityNotFoundException e, final HttpServletRequest request,
                                   Writer writer) throws IOException {
        writer.write(String.format(
                "{\"error\":{\"java.class\":\"%s\", \"error\":\"%s\"}}",
                e.getClass(), e.getError().getId()));
    }

    @RequestMapping(value = "{topicId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getMessages(@PathVariable("topicId") Integer topicId) throws EntityNotFoundException, InterruptedException {
        Integer perPage = Integer.parseInt(request.getParameter("per_page"));
        Integer page = Integer.parseInt(request.getParameter("page"));
        String order = request.getParameter("order");
        String sort = request.getParameter("sort");
        return messageService.getMessagesByTopicIdJSON(topicId, perPage, page, order, sort);
    }

    @RequestMapping(value = "{topicId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Message create(@PathVariable("topicId") Integer topicId,
                          @RequestBody Message message) throws EntityNotFoundException {
        return messageService.addMessageToTopic(topicId, message);
    }

    @RequestMapping(value = "/{topicId}/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable("topicId") Integer topicId, @PathVariable("id") Integer id) throws InterruptedException {
        messageService.remove(id);
    }
}
