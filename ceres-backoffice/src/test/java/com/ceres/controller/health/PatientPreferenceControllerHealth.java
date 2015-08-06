package com.ceres.controller.health;

import com.ceres.controller.v1.human.PatientPreferencesController;
import com.ceres.domain.embedabble.Contact;
import com.ceres.domain.embedabble.Name;
import com.ceres.domain.embedabble.Portion;
import com.ceres.domain.entity.human.Gender;
import com.ceres.domain.entity.human.Patient;
import com.ceres.domain.entity.human.PatientBuilder;
import com.ceres.domain.entity.taco.Food;
import com.ceres.domain.entity.taco.FoodBuilder;
import com.ceres.domain.entity.taco.FoodCategory;
import com.ceres.domain.entity.taco.MeasureUnit;
import com.ceres.domain.repository.human.PatientRepository;
import com.ceres.domain.repository.taco.CategoryRepository;
import com.ceres.domain.repository.taco.FoodRepository;
import com.ceres.suites.integrated.AbstractDbEnvTestSuite;
import org.glassfish.jersey.server.internal.routing.UriRoutingContext;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by leonardo on 18/04/15.
 */
public class PatientPreferenceControllerHealth extends AbstractDbEnvTestSuite {

    @Inject
    PatientPreferencesController patientPreferencesController;

    @Inject
    PatientRepository patientRepository;

    @Inject
    FoodRepository foodRepository;

    @Inject
    CategoryRepository categoryRepository;

    Patient patient;
    Food food;

    @Before
    public void before() {
        FoodCategory cat = categoryRepository.save(new FoodCategory("Test"));

        patientPreferencesController.setUriInfo(new UriRoutingContext(getFakeContainerRequest()));

        patient = new PatientBuilder()
                .setName(new Name("Sr.", "Leonardo", "Rogerio"))
                .setCpf("123123123")
                .setBirthdate(LocalDate.of(2014, 12, 12))
                .setGender(Gender.MALE)
                .setContact(new Contact("abc@email.com"))
                .setPassword("leo")
                .build();

        patient = patientRepository.save(patient);

        food = foodRepository.save(new FoodBuilder()
                .setFoodCategory(cat)
                .setName("Pão")
                .setPortion(new Portion(2.00, MeasureUnit.G))
                .build());
    }

    @Test
    public void patientControllerHealth() {
        Response preferenceResponse;

        preferenceResponse = patientPreferencesController.getAllPreferences(patient.getId());
        assertEquals(Response.Status.OK, preferenceResponse.getStatusInfo());

        preferenceResponse = patientPreferencesController.addPreference(patient.getId(), new ArrayList<>(Arrays.<Long>asList(food.getId())));
        assertEquals(Response.Status.CREATED, preferenceResponse.getStatusInfo());

        preferenceResponse = patientPreferencesController.removePreference(patient.getId(), food.getId());
        assertEquals(Response.Status.NO_CONTENT, preferenceResponse.getStatusInfo());


    }

}
