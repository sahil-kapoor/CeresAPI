package com.ceres.controller.health;

import com.ceres.controller.v1.human.UserController;
import com.ceres.domain.embedabble.Contact;
import com.ceres.domain.embedabble.Name;
import com.ceres.domain.entity.human.*;
import com.ceres.domain.repository.human.HumanRepository;
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
public class UserControllerHealth extends AbstractDbEnvTestSuite {

    @Inject
    UserController userController;

    @Inject
    HumanRepository humanRepo;

    SystemUser user;

    Patient p;

    @Before
    public void before() {

        userController.setUriInfo(new UriRoutingContext(getFakeContainerRequest()));

        p = new PatientBuilder()
                .setName(new Name("Sr.", "Leonardo", "Rogerio"))
                .setCpf("123123123")
                .setBirthdate(LocalDate.of(2014, 12, 12))
                .setGender(Gender.MALE)
                .setContact(new Contact("leo.rog@outlook.com"))
                .setPassword("leo")
                .build();

        p = humanRepo.save(p);

        user = SystemUser.createUser("leo.rog@outlook.com", "leoleo", UserCategory.PATIENT);
        user.setHuman(p);

    }

    @Test
    public void userControllerHealth() {
        Response userResponse;

        userResponse = userController.createUser(user.getHuman().getId(), "leoleo");
        assertEquals(Response.Status.CREATED, userResponse.getStatusInfo());

        userResponse = userController.getUser(1L);
        assertEquals(Response.Status.OK, userResponse.getStatusInfo());

//        userResponse = userController.doLogin(user.getUsername(), "leoleo");
//        assertEquals(Response.Status.OK, userResponse.getStatusInfo());
//        assertNotNull(userResponse.getHeaderString("Authorization"));
//        assertNotNull(userResponse.getHeaderString("Authentication"));

        userResponse = userController.getAllUser();
        assertEquals(Response.Status.OK, userResponse.getStatusInfo());

        userResponse = userController.updateUser(1L, user);
        assertEquals(Response.Status.OK, userResponse.getStatusInfo());

        userResponse = userController.deleteUser(1L);
        assertEquals(Response.Status.NO_CONTENT, userResponse.getStatusInfo());
    }

}
