package com.ceres.controller.health;

import com.ceres.controller.v1.script.ScriptController;
import com.ceres.domain.entity.human.Gender;
import com.ceres.domain.entity.script.Script;
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
public class ScriptControllerHealth extends AbstractDbEnvTestSuite {

    @Inject
    ScriptController scriptController;

    Script s;

    @Before
    public void before() {

        scriptController.setUriInfo(new UriRoutingContext(getFakeContainerRequest()));

        s = new Script();
        s.setName("Porcentagem de Gordura");
        s.setMnemonic("PORCENTAGEM_GORDURA");
        s.setCategory(null);
        s.setTargetGender(Gender.FEMALE);
        s.setScriptContent("return 10 + 10;");

    }

    @Test
    public void scriptControllerHealth() {
        Response scritpResponse;

        scritpResponse = scriptController.createScript(s);
        assertEquals(Response.Status.CREATED, scritpResponse.getStatusInfo());

        scritpResponse = scriptController.getScript(1L);
        assertEquals(Response.Status.OK, scritpResponse.getStatusInfo());

        scritpResponse = scriptController.getScripts();
        assertEquals(Response.Status.OK, scritpResponse.getStatusInfo());

        scritpResponse = scriptController.updateScript(1L, s);
        assertEquals(Response.Status.OK, scritpResponse.getStatusInfo());

        scritpResponse = scriptController.deleteScript(1L);
        assertEquals(Response.Status.NO_CONTENT, scritpResponse.getStatusInfo());
    }

}
