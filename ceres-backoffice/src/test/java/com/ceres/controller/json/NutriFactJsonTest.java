package com.ceres.controller.json;


import com.ceres.domain.entity.taco.MeasureUnit;
import com.ceres.domain.entity.taco.NutriFact;
import com.ceres.domain.entity.taco.NutriFactType;
import org.junit.Before;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by Leonardo on 25/01/2015.
 */
public class NutriFactJsonTest extends JsonTester {

    NutriFact nutriFact;
    private static final String NUTRIFACT_JSON = "{jsonID:1,id:21,name:Colesterol,measureUnit:MG}";

    @Before
    public void prepare() throws JAXBException {
        initializeJsonMapper();
        nutriFact = new NutriFact(NutriFactType.CHOLESTEROL, MeasureUnit.MG);
        nutriFact.setId((long) 21);
    }

    @Override
    public void shouldSerializeToJson() throws IOException {
        String json = mapper.writeValueAsString(nutriFact).replace("\"", "");
        assertEquals(NUTRIFACT_JSON, json);
    }

    @Override
    public void shouldDeserializeFromJson() throws IOException {
        String json = mapper.writeValueAsString(nutriFact);
        NutriFact nutriFactDeserialized = mapper.readValue(json, NutriFact.class);

        assertNotNull(nutriFactDeserialized);
        assertTrue(nutriFact.equals(nutriFactDeserialized));
    }

}
