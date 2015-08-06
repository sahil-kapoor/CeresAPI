package com.ceres.controller.health;

import com.ceres.controller.v1.human.NutritionistController;
import com.ceres.domain.embedabble.Contact;
import com.ceres.domain.embedabble.Name;
import com.ceres.domain.entity.human.Gender;
import com.ceres.domain.entity.human.Nutritionist;
import com.ceres.domain.entity.human.NutritionistBuilder;
import com.ceres.service.human.NutritionistService;
import com.ceres.service.human.exception.HumanException;
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
public class NutritionistControllerHealth extends AbstractDbEnvTestSuite {

    @Inject
    NutritionistController nutritionistController;

    @Inject
    NutritionistService nutritionistService;

    Nutritionist n;

    @Before
    public void before() {

        nutritionistController.setUriInfo(new UriRoutingContext(getFakeContainerRequest()));

        n = new NutritionistBuilder()
                .setName(new Name("Sr.", "Leonardo", "Rogerio"))
                .setBirthdate(LocalDate.of(2014, 12, 12))
                .setGender(Gender.MALE)
                .setContact(new Contact("abc@email.com"))
                .setPassword("leo")
                .setCRN("C192Ca!")
                .build();

        try {
            nutritionistService.create(n);
        } catch (HumanException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void nutritionistControllerHealth() {
        Response nutritionistResponse;

        nutritionistResponse = nutritionistController.getNutritionist(n.getId());
        assertEquals(Response.Status.OK, nutritionistResponse.getStatusInfo());

        nutritionistResponse = nutritionistController.updateNutritionist(n.getId(), n);
        assertEquals(Response.Status.OK, nutritionistResponse.getStatusInfo());

    }

}
