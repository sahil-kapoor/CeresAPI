package com.ceres.controller.health;

import com.ceres.controller.v1.util.TypesController;
import com.ceres.suites.integrated.AbstractDbEnvTestSuite;
import org.glassfish.jersey.server.internal.routing.UriRoutingContext;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

/**
 * Created by leonardo on 18/04/15.
 */
public class TypeControllerHealth extends AbstractDbEnvTestSuite {

    @Inject
    TypesController typeController;

    @Before
    public void before() {

        typeController.setUriInfo(new UriRoutingContext(getFakeContainerRequest()));

    }

    @Test
    public void userControllerHealth() {
        Response userResponse;

        userResponse = typeController.getActivityRates();
        assertEquals(Response.Status.OK, userResponse.getStatusInfo());

        userResponse = typeController.getMealTypes();
        assertEquals(Response.Status.OK, userResponse.getStatusInfo());

        userResponse = typeController.getMeasureUnits();
        assertEquals(Response.Status.OK, userResponse.getStatusInfo());

        userResponse = typeController.getNutrifacts();
        assertEquals(Response.Status.OK, userResponse.getStatusInfo());

        userResponse = typeController.getParameterTypes();
        assertEquals(Response.Status.OK, userResponse.getStatusInfo());

    }

}
