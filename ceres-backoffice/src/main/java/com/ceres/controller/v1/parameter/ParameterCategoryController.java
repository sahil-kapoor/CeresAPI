package com.ceres.controller.v1.parameter;

import com.ceres.controller.RestController;
import com.ceres.domain.entity.parameter.Parameter;
import com.ceres.domain.entity.parameter.ParameterCategory;
import com.ceres.service.parameter.ParameterCategoryService;
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
@Named("ParameterCategoryController")
@Singleton
@Path(RestController.V1)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Medidas", basePath = "v1", description = "manipular categorias de medida")
public final class ParameterCategoryController {

    protected static final String RESOURCE_URL = "medida/categoria";

    private ParameterCategoryService parameterCategoryService;

    private static final Logger logger = LoggerFactory.getLogger(ParameterCategoryController.class);

    private UriInfo uriInfo;

    @Inject
    public ParameterCategoryController(ParameterCategoryService parameterService) {
        this.parameterCategoryService = parameterService;
        logger.info("ParameterCategoryController ready...");
    }

    @GET
    @Path(RESOURCE_URL)
    @ApiOperation(value = "recuperar todas as categorias de medida", responseContainer = "Array")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "todas as categorias", response = Parameter.class)
    })
    public Response getAllCategories() {

        List<ParameterCategory> allParameter = parameterCategoryService.getAll();

        return Response.ok(allParameter).build();
    }

    @GET
    @Path(RESOURCE_URL + "/{id}")
    @ApiOperation(value = "recuperar uma categoria de medida dado seu ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "a categoria", response = ParameterCategory.class),
            @ApiResponse(code = 404, message = "categoria não encontrada")
    })
    public Response getParameter(@PathParam("id") Long categoryId) {
        if (!parameterCategoryService.exists(categoryId)) {
            logger.error("Parameter " + categoryId + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        ParameterCategory param = parameterCategoryService.getById(categoryId);

        return Response.ok(param).build();

    }

    @POST
    @Path(RESOURCE_URL)
    @ApiOperation(value = "criar uma categoria de medida")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "categoria crida", response = ParameterCategory.class),
            @ApiResponse(code = 500, message = "erro ao criar categoria de medida")
    })
    public Response createParameter(@ApiParam ParameterCategory parameterCategory) {

        ParameterCategory p = parameterCategoryService.createOrUpdate(parameterCategory);

        URI createdURI = URI.create(uriInfo.getRequestUri() + "/" + p.getId());
        return Response.created(createdURI).entity(p).build();

    }

    @PUT
    @Path(RESOURCE_URL + "/{id}")
    @ApiOperation(value = "atualizar uma categoria de medida")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "categoria atualizada", response = ParameterCategory.class),
            @ApiResponse(code = 404, message = "categoria não encontrada"),
            @ApiResponse(code = 500, message = "erro ao atualizar categoria de medida")
    })
    public Response updateParameter(@ApiParam ParameterCategory parameterCategory, @PathParam("id") Long parameterId) {
        if (!parameterCategoryService.exists(parameterId)) {
            logger.error("Parameter " + parameterId + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        ParameterCategory p = parameterCategoryService.createOrUpdate(parameterCategory);

        return Response.ok().entity(p).build();

    }

    @DELETE
    @Path(RESOURCE_URL + "/{id}")
    @ApiOperation(value = "remover uma categoria de medida")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "categoria removida"),
            @ApiResponse(code = 404, message = "categoria não encontrada"),
            @ApiResponse(code = 500, message = "erro ao remover categoria de medida")
    })
    public Response deleteParameter(@PathParam("id") Long parameterId) {
        if (!parameterCategoryService.exists(parameterId)) {
            logger.error("Parameter " + parameterId + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        parameterCategoryService.delete(parameterId);

        return Response.noContent().build();

    }

    @Context
    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }
}
