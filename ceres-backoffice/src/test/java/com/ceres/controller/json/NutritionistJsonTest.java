package com.ceres.controller.json;


import com.ceres.domain.embedabble.Name;
import com.ceres.domain.entity.human.Gender;
import com.ceres.domain.entity.human.Nutritionist;
import com.ceres.domain.entity.human.NutritionistBuilder;
import org.junit.Before;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Created by Leonardo on 25/01/2015.
 */
public class NutritionistJsonTest extends JsonTester {

    Nutritionist nutritionist;

    private static final String NUTRITIONIST_JSON = "{jsonID:1,id:null,name:{firstName:Rogerio,lastName:Leonardo,treatment:Sr.},contact:null,gender:MALE,birthdate:2014-12-12,crn:333}";


    @Before
    public void prepare() throws JAXBException {
        initializeJsonMapper();
        nutritionist = new NutritionistBuilder()
                .setName(new Name("Sr.", "Rogerio", "Leonardo"))
                .setGender(Gender.MALE)
                .setBirthdate(LocalDate.of(2014, 12, 12))
                .setCRN("333")
                .build();
    }


    @Override
    public void shouldSerializeToJson() throws IOException {
        String json = mapper.writeValueAsString(nutritionist).replace("\"", "");
        assertEquals(NUTRITIONIST_JSON, json);
    }

    @Override
    public void shouldDeserializeFromJson() throws IOException {
        String json = mapper.writeValueAsString(nutritionist);
        Nutritionist nutritionistDeserialized = mapper.readValue(json, Nutritionist.class);

        assertNotNull(nutritionistDeserialized);
        assertTrue(nutritionist.equals(nutritionistDeserialized));

    }
}
