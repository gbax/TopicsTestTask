package com.gbax.TopicsTestTask.utils.converters;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;

/**
 * Created by abayanov
 * Date: 18.08.14
 */
public class ObjectsMapper extends ObjectMapper {

    public ObjectsMapper() {
        SimpleModule module = new SimpleModule("message module", new Version(1, 0, 0, "SNAPSHOT"));
        module.addSerializer(new MessageJsonSerializer());
        this.registerModule(module);
        module = new SimpleModule("topic module", new Version(1, 0, 0, "SNAPSHOT"));
        module.addSerializer(new TopicJsonSerializer());
        this.registerModule(module);
    }

}
