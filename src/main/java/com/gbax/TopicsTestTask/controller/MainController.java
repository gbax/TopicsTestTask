package com.gbax.TopicsTestTask.controller;

import com.gbax.TopicsTestTask.dao.entity.Topic;
import com.gbax.TopicsTestTask.enums.Errors;
import com.gbax.TopicsTestTask.service.TopicService;
import com.gbax.TopicsTestTask.system.exception.EntityNotFoundException;
import com.gbax.TopicsTestTask.system.exception.NotAuthorizedException;
import com.gbax.TopicsTestTask.system.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;


@Controller
@RequestMapping("/")
public class MainController {

    @Qualifier("topicService")
    @Autowired
    TopicService topicService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    SecurityService securityService;

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public void handleEntityNotFoundException(final EntityNotFoundException e, final HttpServletRequest request,
                                 Writer writer) throws IOException {
        writer.write(String.format(
                "{\"error\":{\"java.class\":\"%s\", \"error\":\"%s\"}}",
                e.getClass(), e.getError().getId()));
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public void handleNotAuthorizedException(final NotAuthorizedException e, final HttpServletRequest request,
                                             Writer writer) throws IOException {
        writer.write(String.format(
                "{\"error\":{\"java.class\":\"%s\", \"error\":\"%s\"}}",
                e.getClass(), e.getError().getId()));
    }

    public void fillDatabase() throws EntityNotFoundException {
        topicService.fillDatabase();
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
            try {
                int id = Integer.parseInt(error);
                Errors errorType = Errors.getById(id);
                model.addObject("errors", Arrays.asList(errorType.getMessage()));
            } catch (NumberFormatException e) {
                model.addObject("errors", Arrays.asList(Errors.INVALID_PARAM.getMessage()));
            }
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

