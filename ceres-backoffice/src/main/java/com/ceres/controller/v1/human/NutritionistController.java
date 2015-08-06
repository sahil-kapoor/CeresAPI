package com.ceres.controller.v1.human;

import com.ceres.controller.RestController;
import com.ceres.domain.entity.human.Nutritionist;
import com.ceres.service.human.NutritionistService;
import com.ceres.service.human.exception.HumanException;
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

/**
 * Created by Leonardo on 25/03/15.
 */
@Named("NutritionistController")
@Singleton
@Path(RestController.V1)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Nutricionistas", basePath = "v1", description = "maniplar nutricionistas", produces = MediaType.APPLICATION_JSON)
public final class NutritionistController {

    public static final String RESOURCE_URL = "nutricionista";

    private NutritionistService nutritionistService;

    private UriInfo uriInfo;

    private static final Logger logger = LoggerFactory.getLogger(NutritionistController.class);

    @Inject
    public NutritionistController(NutritionistService nutritionistService) {
        this.nutritionistService = nutritionistService;
        logger.info("NutritionistController ready...");
    }

    @GET
    @Path(RESOURCE_URL + "/{id}")
    @ApiOperation(value = "recuperar nutricionista dado um ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "o nutricionista", response = Nutritionist.class)
    })
    public Response getNutritionist(@PathParam("id") Long id) {
        if (!nutritionistService.exists(id)) {
            logger.error("Nutritionist " + id + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        Nutritionist n = nutritionistService.getById(id);

        return Response
                .ok(n)
                .build();

    }

    @PUT
    @Path(RESOURCE_URL + "/{id}")
    @ApiOperation(value = "atualizar nutricionista",
            consumes = MediaType.APPLICATION_JSON
    )
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "nutricionista não encontrado"),
            @ApiResponse(code = 500, message = "erro ao atualizar nutricionista"),
            @ApiResponse(code = 200, message = "nutricionista atualizado", response = Nutritionist.class)
    })
    public Response updateNutritionist(@PathParam("id") Long id, @ApiParam Nutritionist nutritionist) {
        if (!nutritionistService.exists(id)) {
            logger.error("Nutritionist " + id + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        try {
            nutritionist = nutritionistService.create(nutritionist);
        } catch (HumanException e) {
            logger.error("Cannot update nutritionist " + id, e.getMessage(), e.getStackTrace());
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }

        return Response
                .ok()
                .entity(nutritionist)
                .build();
    }

    @Context
    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }
}
