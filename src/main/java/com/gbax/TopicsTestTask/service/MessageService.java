package com.gbax.TopicsTestTask.service;

import com.gbax.TopicsTestTask.dao.MessageDao;
import com.gbax.TopicsTestTask.dao.entity.Message;
import com.gbax.TopicsTestTask.dao.entity.Topic;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    MessageDao messageDao;

    public void addMessage(Message message) {
        messageDao.addMessage(message);
    }

    public List<Message> getMessageByTopicId(Integer id) {
        return messageDao.getMessageByTopicId(id);
    }

    public List getMessagesByTopic(Topic topic) {
        return messageDao.getMessagesByTopic(topic);
    }


    public String getMessagesByTopicIdJSON(Integer id) {
        class MessageSerializer implements JsonSerializer<Message>
        {
            @Override
            public JsonElement serialize(Message src, Type typeOfSrc, JsonSerializationContext context)
            {
                JsonObject jsonObject = new JsonObject();
                jsonObject.add("id", new JsonPrimitive(String.valueOf(src.getId())));
                jsonObject.add("message", new JsonPrimitive(src.getMesssage()));
                return jsonObject;
            }
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Message.class, new MessageSerializer());
        Gson gson = gsonBuilder.create();

        return gson.toJson(getMessageByTopicId(id));
    }
}
