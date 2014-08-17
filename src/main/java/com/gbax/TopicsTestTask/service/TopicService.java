package com.gbax.TopicsTestTask.service;

import com.gbax.TopicsTestTask.dao.TopicDao;
import com.gbax.TopicsTestTask.dao.entity.Topic;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;


@Service
public class TopicService {

    @Qualifier("topicDao")
    @Autowired
    TopicDao topicDao;

    public void addTopic(Topic topic) {
        topicDao.addTopic(topic);
    }

    public Topic getTopicById(Integer id) {
        return topicDao.getTopicById(id);
    }

    public List<Topic> getTopics() {
        return topicDao.getTopics();
    }

    public String getTopicsJSON() {

        class TopicSerializer implements JsonSerializer<Topic>
        {
            @Override
            public JsonElement serialize(Topic src, Type typeOfSrc, JsonSerializationContext context)
            {
                JsonObject jsonObject = new JsonObject();
                jsonObject.add("id", new JsonPrimitive(String.valueOf(src.getId())));
                jsonObject.add("description", new JsonPrimitive(src.getDescription()));
                return jsonObject;
            }
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Topic.class, new TopicSerializer());
        Gson gson = gsonBuilder.create();

       return gson.toJson(getTopics());
    }


}
