package com.gbax.TopicsTestTask.utils.converters;

import com.gbax.TopicsTestTask.dao.entity.Topic;
import com.gbax.TopicsTestTask.dao.entity.User;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

/**
 * Created by abayanov
 * Date: 19.08.14
 */
public class TopicJsonSerializer extends JsonSerializer<Topic> {

    @Override
    public Class<Topic> handledType() {
        return Topic.class;
    }

    @Override
    public void serialize(Topic topic, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = principal instanceof User ? (User) principal : null;

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", topic.getId());
        jsonGenerator.writeStringField("description", topic.getDescription());
        if (topic.getUser() != null) {
            jsonGenerator.writeNumberField("userId", topic.getUser().getId());
            if (user != null) {
                jsonGenerator.writeBooleanField("canDelete", topic.getUser().getId().equals(user.getId()));
            }
        }
        jsonGenerator.writeEndObject();
    }
}
