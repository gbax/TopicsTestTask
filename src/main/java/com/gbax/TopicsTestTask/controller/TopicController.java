package com.gbax.TopicsTestTask.controller;

import com.gbax.TopicsTestTask.dao.entity.Topic;
import com.gbax.TopicsTestTask.enums.Errors;
import com.gbax.TopicsTestTask.service.TopicService;
import com.gbax.TopicsTestTask.system.exception.EntityNotFoundException;
import com.gbax.TopicsTestTask.system.exception.NotAuthorizedException;
import com.gbax.TopicsTestTask.system.security.api.ISecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Writer;

/**
 * Контроллер для работы с топиками
 * Created by abayanov
 * Date: 18.08.14
 */
@Controller
@RequestMapping("/topics")
public class TopicController {

    final Logger logger = LoggerFactory.getLogger(TopicController.class);

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
    @ExceptionHandler(NotAuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public void handleNotAuthorizedException(final NotAuthorizedException e,
                                             Writer writer) throws IOException {
        writer.write(String.format(
                "{\"error\":{\"java.class\":\"%s\", \"error\":\"%s\"}}",
                e.getClass(), e.getError().getId()));
    }

    /**
     * @return страница с топиками
     */
    @RequestMapping(method = RequestMethod.GET, produces = "text/plain")
    @ResponseBody
    public String topics() {
        Integer perPage = Integer.parseInt(request.getParameter("per_page"));
        Integer page = Integer.parseInt(request.getParameter("page"));
        String order = request.getParameter("order");
        String sort = request.getParameter("sort");
        return topicService.getTopicsJSON(perPage, page, order, sort);
    }

    /**
     * Создание топика
     *
     * @param topic добавляемый топик
     * @return созданный топик
     * @throws NotAuthorizedException пользователь не авторизован
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Topic create(@RequestBody Topic topic) throws NotAuthorizedException {
        if (securityService.getSecurityPrincipal() == null) {
            throw new NotAuthorizedException(Errors.NOT_AUTHORIZED);
        }
        return topicService.addTopic(topic);
    }

    /**
     * Удаление топика
     *
     * @param id ID удаляемого топика
     * @throws EntityNotFoundException топик не найден
     * @throws NotAuthorizedException  пользователь не авторизован
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable("id") Integer id) throws EntityNotFoundException, NotAuthorizedException {
        if (securityService.getSecurityPrincipal() == null) {
            throw new NotAuthorizedException(Errors.NOT_AUTHORIZED);
        }
        topicService.remove(id);
    }
}
