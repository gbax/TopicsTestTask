package dao;

import com.gbax.TopicsTestTask.dao.MessageDao;
import com.gbax.TopicsTestTask.dao.entity.Message;
import com.gbax.TopicsTestTask.dao.entity.Topic;
import com.gbax.TopicsTestTask.system.exception.EntityNotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import utils.AbstractDaoTest;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Тест DAO для работы с сообщениями
 * Created by Баянов on 07.02.2015.
 */
public class MessageDaoTest extends AbstractDaoTest {

    @Autowired
    private MessageDao messageDao;

    @Test
    public void messageDaoTest() {
        assertNotNull(messageDao);
    }

    /**
     * Тест добавления сообщения
     */
    @Test
    public void addMessageTest() throws EntityNotFoundException {
        Message message = new Message();
        String messsageText = "Test message";
        message.setMessage(messsageText);
        Message resultMessage = messageDao.addMessage(1, message);
        assertNotNull(resultMessage);
        assertNotNull(resultMessage.getTopic());
        assertNotNull(resultMessage.getUser());
        assertNotNull(resultMessage.getId());
        assertEquals(resultMessage.getMessage(), messsageText);
    }

    /**
     * Тест добавления сообщения в отсутствующем топике
     */
    @Test
    public void addMessageEntityNotFoundTest() {
        Message message = new Message();
        String messsageText = "Test message";
        message.setMessage(messsageText);
        Boolean exceptionThrowed = false;
        try {
            Message resultMessage = messageDao.addMessage(999, message);
        } catch (EntityNotFoundException e) {
            exceptionThrowed = true;
        }
        assert exceptionThrowed;
    }

    /**
     * Тест получения сообщений топика
     */
    @Test
    public void getMessagesByTopicTest() {
        Topic topic = new Topic();
        topic.setId(1);

        List<Message> messages = messageDao.getMessagesByTopic(topic, 10, 0, null, null);
        assertNotNull(messages);
        assertEquals(messages.size(), 5);
        assertEquals(messages.get(0).getId(), new Integer(1));
    }

    /**
     * Тест удаления сообщения в топике
     */
    @Test
    public void removeTest() throws EntityNotFoundException {
        Topic topic = new Topic();
        topic.setId(1);
        List<Message> messages = messageDao.getMessagesByTopic(topic, 10, 0, null, null);
        Message message = messages.get(0);
        Integer id = message.getId();
        messageDao.remove(id);
        messages = messageDao.getMessagesByTopic(topic, 10, 0, null, null);
        message = messages.get(0);
        assertNotEquals(message.getId(), id);
    }

    /**
     * Тест удаления всех сообщений в топике
     */
    @Test
    public void deleteMessagesByTopicTest() {
        Topic topic = new Topic();
        topic.setId(2);
        List<Message> messages = messageDao.getMessagesByTopic(topic, 10, 0, null, null);
        assertEquals(messages.size(), 5);
        messageDao.deleteMessagesByTopic(topic);
        messages = messageDao.getMessagesByTopic(topic, 10, 0, null, null);
        assertEquals(messages.size(), 0);
    }

}
