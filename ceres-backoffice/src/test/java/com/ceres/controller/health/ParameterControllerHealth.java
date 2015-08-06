package com.ceres.controller.health;

import com.ceres.controller.v1.parameter.ParameterController;
import com.ceres.domain.entity.parameter.Parameter;
import com.ceres.domain.entity.parameter.ParameterType;
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
public class ParameterControllerHealth extends AbstractDbEnvTestSuite {

    @Inject
    ParameterController parameterController;

    Parameter p;

    @Before
    public void before() {

        parameterController.setUriInfo(new UriRoutingContext(getFakeContainerRequest()));

        p = new Parameter("ALTURA", "Altura", ParameterType.DOUBLE);
    }

    @Test
    public void parameterControllerHealth() {
        Response parameterResponse;

        parameterResponse = parameterController.createParameter(p);
        assertEquals(Response.Status.CREATED, parameterResponse.getStatusInfo());

        parameterResponse = parameterController.getParameter(p.getId());
        assertEquals(Response.Status.OK, parameterResponse.getStatusInfo());

        parameterResponse = parameterController.getAllParameters();
        assertEquals(Response.Status.OK, parameterResponse.getStatusInfo());

        parameterResponse = parameterController.updateParameter(p, p.getId());
        assertEquals(Response.Status.OK, parameterResponse.getStatusInfo());

        parameterResponse = parameterController.deleteParameter(p.getId());
        assertEquals(Response.Status.NO_CONTENT, parameterResponse.getStatusInfo());

        parameterResponse = parameterController.getParameter(112313L);
        assertEquals(Response.Status.NOT_FOUND, parameterResponse.getStatusInfo());

    }

}
