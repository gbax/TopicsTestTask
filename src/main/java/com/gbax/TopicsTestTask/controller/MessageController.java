package com.gbax.TopicsTestTask.controller;

import com.gbax.TopicsTestTask.dao.entity.Message;
import com.gbax.TopicsTestTask.enums.Errors;
import com.gbax.TopicsTestTask.service.MessageService;
import com.gbax.TopicsTestTask.system.exception.EntityNotFoundException;
import com.gbax.TopicsTestTask.system.exception.NotAuthorizedException;
import com.gbax.TopicsTestTask.system.security.api.ISecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Writer;

/**
 * Контроллер для работы с сообщениями топика
 */
@Controller
@RequestMapping("/topic/messages")
public class MessageController {

    @Autowired
    MessageService messageService;

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
     * Получение страницы в JSON с сообщениями топика
     *
     * @param topicId ID топика
     * @return страница с сообщениями топика
     * @throws EntityNotFoundException топик не найден
     */
    @RequestMapping(value = "{topicId}", method = RequestMethod.GET, produces = "text/plain")
    @ResponseBody
    public String getMessages(@PathVariable("topicId") Integer topicId) throws EntityNotFoundException {
        Integer perPage = Integer.parseInt(request.getParameter("per_page"));
        Integer page = Integer.parseInt(request.getParameter("page"));
        String order = request.getParameter("order");
        String sort = request.getParameter("sort");
        return messageService.getMessagesByTopicIdJSON(topicId, perPage, page, order, sort);
    }

    /**
     * Добавление сообщения в топик
     *
     * @param topicId ID топика
     * @param message добавляемое сообщение
     * @return созданное сообщение
     * @throws EntityNotFoundException топик не найден
     * @throws NotAuthorizedException  пользователь не авторизован
     */
    @RequestMapping(value = "{topicId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Message create(@PathVariable("topicId") Integer topicId,
                          @RequestBody Message message) throws EntityNotFoundException, NotAuthorizedException {
        if (securityService.getSecurityPrincipal() == null) {
            throw new NotAuthorizedException(Errors.NOT_AUTHORIZED);
        }
        return messageService.addMessageToTopic(topicId, message);
    }

    /**
     * Удаление сообщеня из топика
     *
     * @param topicId   ID топика
     * @param messageId ID сообщения
     * @throws EntityNotFoundException топик не найден
     * @throws NotAuthorizedException  пользователь не авторизован
     */
    @RequestMapping(value = "/{topicId}/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable("topicId") Integer topicId,
                       @PathVariable("id") Integer messageId) throws EntityNotFoundException, NotAuthorizedException {
        if (securityService.getSecurityPrincipal() == null) {
            throw new NotAuthorizedException(Errors.NOT_AUTHORIZED);
        }
        messageService.remove(messageId);
    }


}
