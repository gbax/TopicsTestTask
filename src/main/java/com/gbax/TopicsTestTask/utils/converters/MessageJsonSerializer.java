package com.gbax.TopicsTestTask.utils.converters;

import com.gbax.TopicsTestTask.dao.entity.Message;
import com.gbax.TopicsTestTask.dao.entity.User;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

/**
 * Created by abayanov
 * Date: 18.08.14
 */
public class MessageJsonSerializer extends JsonSerializer<Message> {

    @Override
    public Class<Message> handledType() {
        return Message.class;
    }

    @Override
    public void serialize(Message message, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = principal instanceof User ? (User) principal : null;

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", message.getId());
        jsonGenerator.writeStringField("message", message.getMessage());
        if (message.getUser() != null) {
            jsonGenerator.writeNumberField("userId", message.getUser().getId());
            if (user != null) {
                jsonGenerator.writeBooleanField("canDelete", message.getUser().getId().equals(user.getId()));
            }
        }
        jsonGenerator.writeEndObject();
    }
}
