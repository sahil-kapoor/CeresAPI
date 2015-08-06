package com.ceres.controller.v1.parameter;

import com.ceres.controller.RestController;
import com.ceres.domain.entity.parameter.Parameter;
import com.ceres.service.parameter.ParameterService;
import com.wordnik.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

/**
 * Created by leonardo on 18/03/15.
 */
@Named("ParameterController")
@Singleton
@Path(RestController.V1)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Medidas", basePath = "v1", description = "manipular medidas")
public final class ParameterController {

    protected static final String RESOURCE_URL = "medida";

    private ParameterService parameterService;

    private static final Logger logger = LoggerFactory.getLogger(ParameterController.class);

    private UriInfo uriInfo;

    @Inject
    public ParameterController(ParameterService parameterService) {
        this.parameterService = parameterService;
        logger.info("ParameterController ready...");
    }

    @GET
    @Path(RESOURCE_URL)
    @ApiOperation(value = "recuperar todas as medidas", responseContainer = "Array")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "todas as medidas", response = Parameter.class)
    })
    public Response getAllParameters() {

        List<Parameter> allParameter = parameterService.getAll();

        return Response.ok(allParameter).build();
    }

    @GET
    @Path(RESOURCE_URL + "/{id}")
    @ApiOperation(value = "recuperar uma medida dado seu ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "a medida", response = Parameter.class),
            @ApiResponse(code = 404, message = "medida não encontrada")
    })
    public Response getParameter(@PathParam("id") Long parameterId) {
        if (!parameterService.exists(parameterId)) {
            logger.error("Parameter " + parameterId + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        Parameter param = parameterService.getById(parameterId);

        return Response.ok(param).build();

    }

    @POST
    @Path(RESOURCE_URL)
    @ApiOperation(value = "criar nova medida")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "medida criada", response = Parameter.class),
            @ApiResponse(code = 500, message = "erro ao criar medida")
    })
    public Response createParameter(@ApiParam Parameter parameter) {

        Parameter p = parameterService.createOrUpdate(parameter);

        URI createdURI = URI.create(uriInfo.getRequestUri() + "/" + p.getId());
        return Response.created(createdURI).entity(p).build();

    }

    @PUT
    @Path(RESOURCE_URL + "/{id}")
    @ApiOperation(value = "atualizar medida existente medida")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "medida atualizada", response = Parameter.class),
            @ApiResponse(code = 404, message = "medida não encontrada"),
            @ApiResponse(code = 500, message = "erro ao criar medida")
    })
    public Response updateParameter(@ApiParam Parameter parameter, @PathParam("id") Long parameterId) {
        if (!parameterService.exists(parameterId)) {
            logger.error("Parameter " + parameterId + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        parameter.setId(parameterId);
        Parameter p = parameterService.createOrUpdate(parameter);

        return Response.ok().entity(p).build();

    }

    @DELETE
    @Path(RESOURCE_URL + "/{id}")
    @ApiOperation(value = "remover medida dado seu ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "medida removida"),
            @ApiResponse(code = 404, message = "medida não encontrada"),
            @ApiResponse(code = 500, message = "erro ao remover medida")
    })
    public Response deleteParameter(@PathParam("id") Long parameterId) {
        if (!parameterService.exists(parameterId)) {
            logger.error("Parameter " + parameterId + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        parameterService.delete(parameterId);

        return Response.noContent().build();

    }

    @Context
    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }
}
