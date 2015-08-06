package com.ceres.controller.health;

import com.ceres.controller.v1.human.PatientController;
import com.ceres.domain.embedabble.Contact;
import com.ceres.domain.embedabble.Name;
import com.ceres.domain.entity.human.Gender;
import com.ceres.domain.entity.human.Patient;
import com.ceres.domain.entity.human.PatientBuilder;
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
public class PatientControllerHealth extends AbstractDbEnvTestSuite {

    @Inject
    PatientController patientController;

    Patient p;

    @Before
    public void before() {

        patientController.setUriInfo(new UriRoutingContext(getFakeContainerRequest()));

        p = new PatientBuilder()
                .setName(new Name("Sr.", "Leonardo", "Rogerio"))
                .setCpf("123123123")
                .setBirthdate(LocalDate.of(2014, 12, 12))
                .setGender(Gender.MALE)
                .setContact(new Contact("abc@email.com"))
                .setPassword("leo")
                .build();

    }

    @Test
    public void patientControllerHealth() {
        Response patientResponse;


        patientResponse = patientController.createPatient(p);
        assertEquals(Response.Status.CREATED, patientResponse.getStatusInfo());

        patientResponse = patientController.getAllPatients();
        assertEquals(Response.Status.OK, patientResponse.getStatusInfo());

        patientResponse = patientController.getPatient(p.getId());
        assertEquals(Response.Status.OK, patientResponse.getStatusInfo());

        patientResponse = patientController.updatePatient(p.getId(), p);
        assertEquals(Response.Status.OK, patientResponse.getStatusInfo());

        patientResponse = patientController.deletePatient(p.getId());
        assertEquals(Response.Status.NO_CONTENT, patientResponse.getStatusInfo());

        patientResponse = patientController.createPatient(new PatientBuilder().build());
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, patientResponse.getStatusInfo());

        patientResponse = patientController.getPatient(1123L);
        assertEquals(Response.Status.NOT_FOUND, patientResponse.getStatusInfo());
    }

}
