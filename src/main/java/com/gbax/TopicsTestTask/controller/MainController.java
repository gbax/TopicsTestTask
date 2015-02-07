package com.gbax.TopicsTestTask.controller;

import com.gbax.TopicsTestTask.dao.entity.Topic;
import com.gbax.TopicsTestTask.enums.Errors;
import com.gbax.TopicsTestTask.service.TopicService;
import com.gbax.TopicsTestTask.system.exception.EntityNotFoundException;
import com.gbax.TopicsTestTask.system.exception.NotAuthorizedException;
import com.gbax.TopicsTestTask.system.security.api.ISecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;

/**
 * Центральный контроллер
 */
@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    TopicService topicService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    ISecurityService securityService;

    /**
     * Обработка исключения не найденного топика
     *
     * @param e      исключение EntityNotFoundException
     * @param writer поток для вывода сообщения
     * @throws IOException
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public void handleEntityNotFoundException(final EntityNotFoundException e,
                                              Writer writer) throws IOException {
        writer.write(String.format(
                "{\"error\":{\"java.class\":\"%s\", \"error\":\"%s\"}}",
                e.getClass(), e.getError().getId()));
    }

    /**
     * Обработка исключения о попытке доступ к данным неавторизованного пользователя
     *
     * @param e      исключение NotAuthorizedException
     * @param writer поток для вывода сообщения
     * @throws IOException
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public void handleNotAuthorizedException(final NotAuthorizedException e,
                                             Writer writer) throws IOException {
        writer.write(String.format(
                "{\"error\":{\"java.class\":\"%s\", \"error\":\"%s\"}}",
                e.getClass(), e.getError().getId()));
    }

    /**
     * Начальное заполнение БД тестовыми данными
     *
     * @throws EntityNotFoundException
     */
    public void fillDatabase() throws EntityNotFoundException {
        topicService.fillDatabase();
    }

    /**
     * Роут для перенаправления на главную страницу приложения
     *
     * @return главная страница приложения
     */
    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "redirect:/index";
    }

    /**
     * Роут для отображения страницы при ошибке авторизации
     *
     * @param model модель с признаокм отображения ошибок
     * @return главная страница приложения
     */
    @RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
    public String loginError(ModelMap model) {
        model.addAttribute("error", "true");
        return "index";
    }

    /**
     * @return главная страница приложения
     */
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

    /**
     * Отображение страницы топика
     *
     * @param id id топика
     * @return страница топика
     */
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

