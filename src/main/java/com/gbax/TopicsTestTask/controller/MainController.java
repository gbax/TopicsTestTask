package com.gbax.TopicsTestTask.controller;

import com.gbax.TopicsTestTask.dao.entity.Message;
import com.gbax.TopicsTestTask.dao.entity.Topic;
import com.gbax.TopicsTestTask.dao.entity.User;
import com.gbax.TopicsTestTask.enums.Errors;
import com.gbax.TopicsTestTask.service.MessageService;
import com.gbax.TopicsTestTask.service.TopicService;
import com.gbax.TopicsTestTask.service.UserService;
import com.gbax.TopicsTestTask.system.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;


@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @Autowired
    TopicService topicService;

    @Autowired
    HttpServletRequest request;

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public void handleEntityNotFoundException(final EntityNotFoundException e, final HttpServletRequest request,
                                 Writer writer) throws IOException {
        writer.write(String.format(
                "{\"error\":{\"java.class\":\"%s\", \"error\":\"%s\"}}",
                e.getClass(), e.getError().getId()));
    }

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
            for (int j = 0;j< 20;j++){
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
        String error = request.getParameter("error");
        if (error != null && !"".equals(error)) {
            Errors errorType = Errors.getById(Integer.parseInt(error));
            model.addObject("errors", Arrays.asList(errorType.getMessage()));
        }
        return model;
    }

    @RequestMapping(value = "/topic/{id}", method = RequestMethod.GET)
    public ModelAndView showTopicForm(@PathVariable("id") Integer id) {
        ModelAndView model = new ModelAndView("topic");
        try {
            Topic topic = topicService.getTopicById(id);
            model.addObject("topic", topic);
         } catch (EntityNotFoundException ex) {
            return new ModelAndView(String.format("redirect:/index?error=%s", ex.getError().getId()));
        }
        return model;
    }
}

