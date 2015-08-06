package com.ceres.controller.health;

import com.ceres.controller.v1.menu.NutritionistMenuController;
import com.ceres.controller.v1.menu.PatientMenuController;
import com.ceres.domain.embedabble.Contact;
import com.ceres.domain.embedabble.Name;
import com.ceres.domain.entity.human.*;
import com.ceres.domain.entity.menu.Menu;
import com.ceres.domain.entity.menu.MenuBuilder;
import com.ceres.domain.repository.human.NutritionistRepository;
import com.ceres.domain.repository.human.PatientRepository;
import com.ceres.suites.integrated.AbstractDbEnvTestSuite;
import org.glassfish.jersey.server.internal.routing.UriRoutingContext;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

/**
 * Created by leonardo on 18/04/15.
 */
public class MenuControllerHealth extends AbstractDbEnvTestSuite {

    @Inject
    PatientMenuController patientMenuController;

    @Inject
    NutritionistMenuController nutritionistMenuController;

    @Inject
    NutritionistRepository nutritionistRepository;

    @Inject
    PatientRepository patientRepository;

    Menu m;

    Nutritionist nutritionist;

    Patient patient;

    @Before
    public void before() {

        patientMenuController.setUriInfo(new UriRoutingContext(getFakeContainerRequest()));
        nutritionistMenuController.setUriInfo(new UriRoutingContext(getFakeContainerRequest()));


        patient = new PatientBuilder()
                .setName(new Name("Sr.", "Leonardo", "Rogerio"))
                .setCpf("123123123")
                .setBirthdate(LocalDate.of(2014, 12, 12))
                .setGender(Gender.MALE)
                .setContact(new Contact("pati@email.com"))
                .setPassword("leo")
                .build();

        patient = patientRepository.save(patient);

        nutritionist = new NutritionistBuilder()
                .setName(new Name("Sr.", "Leonardo", "Rogerio"))
                .setBirthdate(LocalDate.of(2014, 12, 12))
                .setGender(Gender.MALE)
                .setContact(new Contact("nutri@email.com"))
                .setPassword("leo")
                .setCRN("C192Ca!")
                .build();

        nutritionist = nutritionistRepository.save(nutritionist);

        m = new MenuBuilder()
                .withName("Perde Peso III")
                .setValidityDate(LocalDate.of(2014, 12, 18))
                .setCreationDate(LocalDate.of(2014, 12, 12))
                .setNutritionist(nutritionist)
                .build();
    }

    @Test
    public void menuControllerHealth() {
        Response menuResponse;

        menuResponse = nutritionistMenuController.createMenu(nutritionist.getId(), patient.getId(), m);
        assertEquals(Response.Status.CREATED, menuResponse.getStatusInfo());

        menuResponse = nutritionistMenuController.getAllNutritionistMenu(nutritionist.getId());
        assertEquals(Response.Status.OK, menuResponse.getStatusInfo());

        menuResponse = nutritionistMenuController.getMenu(m.getId());
        assertEquals(Response.Status.OK, menuResponse.getStatusInfo());

        menuResponse = nutritionistMenuController.updateMenu(m.getId(), m);
        assertEquals(Response.Status.OK, menuResponse.getStatusInfo());

        menuResponse = patientMenuController.getAllPatientsMenu(patient.getId());
        assertEquals(Response.Status.OK, menuResponse.getStatusInfo());

        menuResponse = patientMenuController.getPatientsMenu(m.getId());
        assertEquals(Response.Status.OK, menuResponse.getStatusInfo());

        menuResponse = nutritionistMenuController.deleteMenu(m.getId());
        assertEquals(Response.Status.NO_CONTENT, menuResponse.getStatusInfo());
    }

}
