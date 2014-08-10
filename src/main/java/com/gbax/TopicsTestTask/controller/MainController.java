package com.gbax.TopicsTestTask.controller;
import org.springframework.stereotype.Controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "redirect:/topicList";
    }

    @RequestMapping(value = "topicList", method = RequestMethod.GET)
    public ModelAndView showMainForm() {
        ModelAndView model = new ModelAndView("index");
        model.addObject("message", "Start backbone application");
        return model;
    }

}

