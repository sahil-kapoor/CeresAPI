package com.ceres.controller.health;

import com.ceres.controller.v1.human.FeatureController;
import com.ceres.domain.embedabble.Contact;
import com.ceres.domain.embedabble.Name;
import com.ceres.domain.entity.human.*;
import com.ceres.domain.entity.parameter.*;
import com.ceres.domain.repository.human.PatientRepository;
import com.ceres.domain.repository.parameter.ParameterRepository;
import com.ceres.suites.integrated.AbstractDbEnvTestSuite;
import org.glassfish.jersey.server.internal.routing.UriRoutingContext;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by leonardo on 18/04/15.
 */
public class FeatureControllerHealth extends AbstractDbEnvTestSuite {

    @Inject
    FeatureController featureController;

    @Inject
    PatientRepository patientRepository;

    @Inject
    ParameterRepository parameterRepository;

    Feature feature;

    Patient patient;

    @Before
    public void before() {

        featureController.setUriInfo(new UriRoutingContext(getFakeContainerRequest()));

        parameterRepository.save(new Parameter("ALTURA", "Altura", ParameterType.DOUBLE));
        parameterRepository.save(new Parameter("PESO", "Peso", ParameterType.DOUBLE));
        parameterRepository.save(new Parameter("TAXA_DE_ATIVIDADE", "Taxa de Atividade", ParameterType.ACTIVITY_RATE));

        patient = new PatientBuilder()
                .setName(new Name("Sr.", "Leonardo", "Rogerio"))
                .setCpf("123123123")
                .setBirthdate(LocalDate.of(2014, 12, 12))
                .setGender(Gender.MALE)
                .setContact(new Contact("abc@email.com"))
                .setPassword("leo")
                .build();

        patient = patientRepository.save(patient);

        feature = new FeatureBuilder()
                .setVisitDate(LocalDate.of(2014, 12, 12))
                .addParameterValues(createBasicParameters())
                .setPatient(patient)
                .build();
        feature.getFeatureParameters();
    }

    @Test
    public void scriptControllerHealth() {
        Response featureResponse;

        featureResponse = featureController.createPatientFeature(patient.getId(), feature);
        assertEquals(Response.Status.CREATED, featureResponse.getStatusInfo());

        featureResponse = featureController.getAllPatientsFeature(patient.getId());
        assertEquals(Response.Status.OK, featureResponse.getStatusInfo());

        featureResponse = featureController.getPatientsFeature(feature.getId());
        assertEquals(Response.Status.OK, featureResponse.getStatusInfo());

        featureResponse = featureController.updateFeature(feature.getId(), feature);
        assertEquals(Response.Status.OK, featureResponse.getStatusInfo());

        featureResponse = featureController.deleteFeature(patient.getId(), feature.getId());
        assertEquals(Response.Status.NO_CONTENT, featureResponse.getStatusInfo());
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

}
