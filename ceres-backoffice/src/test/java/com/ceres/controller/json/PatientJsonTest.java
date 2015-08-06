package com.ceres.controller.json;


import com.ceres.domain.embedabble.Contact;
import com.ceres.domain.embedabble.Name;
import com.ceres.domain.embedabble.Portion;
import com.ceres.domain.entity.human.*;
import com.ceres.domain.entity.parameter.*;
import com.ceres.domain.entity.taco.FoodBuilder;
import com.ceres.domain.entity.taco.MeasureUnit;
import org.junit.Before;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Leonardo on 25/01/2015.
 */
public class PatientJsonTest extends JsonTester {

    private static final String PATIENT_JSON = "{jsonID:1,id:null,name:{firstName:Leonardo,lastName:Rogerio,treatment:Sr.},contact:{homePhone:null,cellPhone:null,email:abc@email.com},gender:MALE,birthdate:2014-12-12,cpf:123123123,address:null}";
    Patient patient;

    @Before
    public void prepare() throws JAXBException {
        initializeJsonMapper();

        Feature feature = new FeatureBuilder()
                .setVisitDate(LocalDate.of(2014, 12, 12))
                .setPatient(patient)
                .addParameterValues(createBasicParameters())
                .build();
        feature.setId(1L);
        feature.getFeatureParameters();

        patient = new PatientBuilder()
                .setName(new Name("Sr.", "Leonardo", "Rogerio"))
                .setCpf("123123123")
                .setBirthdate(LocalDate.of(2014, 12, 12))
                .setGender(Gender.MALE)
                .addPreference(new FoodBuilder().setName("Arroz").setPortion(new Portion(2.00, MeasureUnit.G)).build())
                .addRestriction(new FoodBuilder().setName("Feijao").setPortion(new Portion(2.00, MeasureUnit.G)).build())
                .setContact(new Contact("abc@email.com"))
                .addFeature(feature)
                .build();

    }

    private List<ParameterValue> createBasicParameters() {

        List<ParameterValue> paramValues = new ArrayList<>();

        ParameterValue paramVal = new ParameterDouble(160.0);
        paramVal.setParameter(new Parameter("ALTURA", ParameterType.DOUBLE));
        paramValues.add(paramVal);

        paramVal = new ParameterDouble(60.00);
        paramVal.setParameter(new Parameter("PESO", ParameterType.DOUBLE));
        paramValues.add(paramVal);

        paramVal = new ParameterActivityRate(ActivityRate.MODERATELY);
        paramVal.setParameter(new Parameter("TAXA_DE_ATIVIDADE", ParameterType.ACTIVITY_RATE));
        paramValues.add(paramVal);

        return paramValues;
    }

    @Override
    public void shouldSerializeToJson() throws IOException {
        String json = mapper.writeValueAsString(patient).replace("\"", "");
        assertEquals(PATIENT_JSON, json);
    }

    @Override
    public void shouldDeserializeFromJson() throws IOException {
        String json = mapper.writeValueAsString(patient);
        Patient patientDeserialized = mapper.readValue(json, Patient.class);

        assertNotNull(patientDeserialized);
        assertTrue(patient.equals(patientDeserialized));

    }


}
