package controller;

import com.gbax.TopicsTestTask.controller.MessageController;
import com.gbax.TopicsTestTask.dao.entity.Message;
import com.gbax.TopicsTestTask.dao.entity.Topic;
import com.gbax.TopicsTestTask.dao.entity.User;
import com.gbax.TopicsTestTask.service.MessageService;
import com.gbax.TopicsTestTask.system.exception.EntityNotFoundException;
import com.gbax.TopicsTestTask.system.exception.NotAuthorizedException;
import com.gbax.TopicsTestTask.system.security.SecurityService;
import utils.AbstractTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Тесты контроллера для работы с сообщениями
 * Created by Баянов on 07.02.2015.
 */
public class MessageControllerTest extends AbstractTest {

    final Logger logger = LoggerFactory.getLogger(MessageControllerTest.class);

    private SecurityService securityServiceHasNotUser;

    private MessageService messageService;
    private MessageService messageServiceEntityNotFound;

    private Message message;

    private MessageController messageController;

    @Before
    public void init() throws ParseException, EntityNotFoundException {
        Topic topic = new Topic();
        topic.setId(0);
        User user = new User();
        user.setId(0);
        user.setName("TUser");
        Date date = (new SimpleDateFormat("dd.MM.yyyy hh:mm")).parse("01.01.2015 01:00");
        message = new Message();
        message.setDate(date);
        message.setMessage("test");
        message.setUser(user);
        message.setTopic(topic);

        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getParameter("per_page")).thenReturn("0");
        when(request.getParameter("page")).thenReturn("0");
        when(request.getParameter("order")).thenReturn("0");
        when(request.getParameter("sort")).thenReturn("0");

        messageService = mock(MessageService.class);
        when(messageService.getMessagesByTopicIdJSON(anyInt(), anyInt(), anyInt(), anyString(), anyString())).thenAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                for (Integer i = 0; i < args.length; i++) {
                    if (args[i] == null) {
                        throw new NullPointerException();
                    }
                }
                return "Test string";
            }
        });

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                if (args[0] == null) {
                    throw new NullPointerException();
                }
                return null;
            }
        }).when(messageService).remove(anyInt());
        when(messageService.addMessageToTopic(anyInt(), (Message) any())).thenAnswer(new Answer<Message>() {
            @Override
            public Message answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                Object o1 = args[0];
                if (o1 == null) {
                    throw new NullPointerException();
                }
                Object o2 = args[1];
                if (o2 == null) {
                    throw new NullPointerException();
                }
                return (Message) o2;
            }
        });

        messageServiceEntityNotFound = mock(MessageService.class);
        when(messageServiceEntityNotFound.addMessageToTopic(anyInt(), (Message) any())).thenThrow(EntityNotFoundException.class);
        doThrow(EntityNotFoundException.class).when(messageServiceEntityNotFound).remove(anyInt());

        SecurityService securityServiceHasUser = mock(SecurityService.class);
        when(securityServiceHasUser.getSecurityPrincipal()).thenReturn(user);

        securityServiceHasNotUser = mock(SecurityService.class);
        when(securityServiceHasNotUser.getSecurityPrincipal()).thenReturn(null);

        messageController = new MessageController();
        ReflectionTestUtils.setField(messageController, "messageService", messageService);
        ReflectionTestUtils.setField(messageController, "securityService", securityServiceHasUser);
        ReflectionTestUtils.setField(messageController, "request", request);
    }

    /**
     * Тест получения сообщения по ID
     */
    @Test
    public void testGetMessages() {
        String messages1 = null;
        try {
            messages1 = messageController.getMessages(0);
        } catch (EntityNotFoundException e) {
            assert false;
        }
        assertEquals(messages1, "Test string");
    }

    /**
     * Тест создания сообщения
     */
    @Test
    public void testCreateMessages() {
        Message resultMessage = null;
        try {
            resultMessage = messageController.create(0, message);
        } catch (EntityNotFoundException | NotAuthorizedException e) {
            assert false;
        }
        assertNotNull(resultMessage);
    }

    /**
     * Тест создания сообщения в отсутствующем топике
     */
    @Test
    public void testCreateMessagesEntityNotFound() {
        ReflectionTestUtils.setField(messageController, "messageService", messageServiceEntityNotFound);
        Message resultMessage = null;
        try {
            resultMessage = messageController.create(0, message);
        } catch (EntityNotFoundException e) {
            assert true;
        } catch (NotAuthorizedException e) {
            assert false;
        }

        assertNull(resultMessage);
    }

    /**
     * Тест попытки создния сообщения неавторизованным пользователем
     */
    @Test
    public void testCreateMessagesNotAutorized() {
        ReflectionTestUtils.setField(messageController, "securityService", securityServiceHasNotUser);
        Message resultMessage = null;
        try {
            resultMessage = messageController.create(0, message);
        } catch (EntityNotFoundException e) {
            assert false;
        } catch (NotAuthorizedException e) {
            assert true;
        }
        assertNull(resultMessage);
    }

    /**
     * Тест удалния сообщения
     */
    @Test
    public void testRemove() {
        try {
            messageController.remove(0, 0);
        } catch (EntityNotFoundException | NotAuthorizedException e) {
            assert false;
        }
        assert true;
    }

    /**
     * Тест попытки удаления отсутствующего соообщения
     */
    @Test
    public void testRemoveMessagesEntityNotFound() {
        ReflectionTestUtils.setField(messageController, "messageService", messageServiceEntityNotFound);
        try {
            messageController.remove(0, 0);
        } catch (EntityNotFoundException e) {
            assert true;
        } catch (NotAuthorizedException e) {
            assert false;
        }

        assert true;
    }

    /**
     * Тест попытки удаления сообщения неавторизованным пользователем
     */
    @Test
    public void testRemoveMessagesNotAutorized() {
        ReflectionTestUtils.setField(messageController, "securityService", securityServiceHasNotUser);
        try {
            messageController.remove(0, 0);
        } catch (EntityNotFoundException e) {
            assert false;
        } catch (NotAuthorizedException e) {
            assert true;
        }
        assert true;
    }

}
