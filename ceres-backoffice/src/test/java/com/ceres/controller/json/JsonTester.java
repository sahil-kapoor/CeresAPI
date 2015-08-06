package com.ceres.controller.json;

import com.ceres.application.configuration.ObjectMapperConfig;
import com.ceres.domain.entity.AbstractEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by leonardo on 11/04/15.
 */
public abstract class JsonTester {

    public ObjectMapper mapper;

    public void initializeJsonMapper() {
        ObjectMapperConfig config = new ObjectMapperConfig();
        mapper = config.getContext(AbstractEntity.class);
        mapper.disable(SerializationFeature.INDENT_OUTPUT);
    }

    @Test
    public abstract void shouldSerializeToJson() throws IOException;

    @Test
    public abstract void shouldDeserializeFromJson() throws IOException;
}
