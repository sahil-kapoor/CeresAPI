package com.ceres.controller.v1.util;

import com.ceres.controller.RestController;
import com.ceres.service.validation.CommonsValidationService;
import com.wordnik.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by leonardo on 19/04/15.
 */
@Named("ValidationController")
@Singleton
@Path(RestController.V1)
@Api(value = "Utilidades", basePath = "v1", description = "instrumentos adicionais")
public final class ValidationController {

    private static final String RESOURCE_URL = "validacao";

    private UriInfo uriInfo;

    private CommonsValidationService validationService;

    private static final Logger logger = LoggerFactory.getLogger(ValidationController.class);

    @Inject
    public ValidationController(CommonsValidationService validationService) {
        this.validationService = validationService;
        logger.info("ValidationController ready...");
    }

    @POST
    @Path(RESOURCE_URL + "/email")
    @Consumes(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "validar disponibilidade do e-mail", consumes = MediaType.TEXT_PLAIN)
    @ApiResponses(value = {
            @ApiResponse(code = 409, message = "email em uso"),
            @ApiResponse(code = 204, message = "email disponível")

    })
    public Response validateEmail(@ApiParam String email) {
        if (validationService.isEmailInUse(email)) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        return Response.noContent().build();
    }

    @POST
    @Path(RESOURCE_URL + "/login")
    @Consumes(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "validar login", consumes = MediaType.TEXT_PLAIN)
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "token inválido"),
            @ApiResponse(code = 200, message = "token válido")

    })
    public Response validateToken(@ApiParam String token) {
        if (!validationService.isTokenValid(token)) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        return Response.ok().build();
    }

    @Context
    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }
}
