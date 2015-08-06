package com.ceres.controller.v1.consumption;

import com.ceres.controller.RestController;
import com.wordnik.swagger.annotations.Api;
import org.springframework.stereotype.Controller;

import javax.inject.Named;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by leonardo on 25/04/15.
 */
@Named("ConsumptionController")
@Path(RestController.V1)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Conversas", basePath = "v1")
public final class ConsumptionController {
}
