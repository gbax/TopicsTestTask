package com.gbax.TopicsTestTask.utils.converters;

import com.gbax.TopicsTestTask.dao.entity.Message;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

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
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", message.getId());
        jsonGenerator.writeStringField("message", message.getMessage());
        if (message.getUser() != null) {
            jsonGenerator.writeNumberField("userId", message.getUser().getId());
        }
        jsonGenerator.writeEndObject();
    }
}
