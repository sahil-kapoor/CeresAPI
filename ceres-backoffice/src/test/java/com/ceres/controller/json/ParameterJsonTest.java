package com.ceres.controller.json;


import com.ceres.domain.entity.parameter.Parameter;
import com.ceres.domain.entity.parameter.ParameterType;
import org.junit.Before;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by Leonardo on 25/01/2015.
 */
public class ParameterJsonTest extends JsonTester {

    Parameter parameter;

    private static final String PARAMETER_JSON = "{jsonID:1,id:null,name:null,mnemonic:ALTURA,type:DOUBLE,category:null}";


    @Before
    public void prepare() throws JAXBException {
        initializeJsonMapper();

        parameter = new Parameter("ALTURA", ParameterType.DOUBLE);

    }


    @Override
    public void shouldSerializeToJson() throws IOException {
        String json = mapper.writeValueAsString(parameter).replace("\"", "");
        assertEquals(PARAMETER_JSON, json);
    }

    @Override
    public void shouldDeserializeFromJson() throws IOException {
        String json = mapper.writeValueAsString(parameter);
        Parameter parameterDeserialized = mapper.readValue(json, Parameter.class);

        assertNotNull(parameterDeserialized);
        assertTrue(parameter.equals(parameterDeserialized));
    }
}
