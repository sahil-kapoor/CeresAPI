package com.ceres.controller.v1.taco;

import com.ceres.controller.RestController;
import com.ceres.domain.entity.taco.Food;
import com.ceres.domain.entity.taco.Measure;
import com.ceres.service.taco.FoodService;
import com.ceres.service.taco.MeasureService;
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
 * Created by Leonardo on 20/01/2015.
 */
@Singleton
@Named("MeasureController")
@Path(RestController.V1)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Medidas de Alimento", basePath = "v1", description = "manipular medidas de alimento")
public final class MeasureController {

    protected static final String RESOURCE_URL = "medida";

    private FoodService foodService;

    private MeasureService measureService;

    private UriInfo uriInfo;

    private static final Logger logger = LoggerFactory.getLogger(MeasureController.class);

    @Inject
    public MeasureController(FoodService foodService, MeasureService measureService) {
        this.measureService = measureService;
        this.foodService = foodService;
        logger.info("MeasureController ready...");
    }

    @GET
    @Path(FoodController.RESOURCE_URL + "/" + RESOURCE_URL + "/{id}")
    @ApiOperation(value = "recupera uma medida caseira de um alimento")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "a medida", response = Measure.class),
            @ApiResponse(code = 404, message = "medida não encontrado")
    })
    public Response getById(@PathParam("id") Long id) {
        if (!measureService.exists(id)) {
            logger.error("Measure " + id + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        Measure measure = measureService.getById(id);

        return Response.ok()
                .entity(measure)
                .build();
    }

    @GET
    @Path(FoodController.RESOURCE_URL + "/{foodId}/" + RESOURCE_URL)
    @ApiOperation(value = "recupera todas as medidas caseiras de um alimento")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "as medidas do alimento", response = Measure.class),
            @ApiResponse(code = 404, message = "alimento não encontrado")
    })
    public Response getByFood(@PathParam("foodId") Long foodId) {
        if (!foodService.exists(foodId)) {
            logger.error("Food " + foodId + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        Food food = foodService.getById(foodId);

        List<Measure> measures = measureService.getByFood(food);

        return Response.ok()
                .entity(measures)
                .build();
    }

    @POST
    @Path(FoodController.RESOURCE_URL + "/{foodId}/" + RESOURCE_URL)
    @ApiOperation(value = "cria uma nova medida para o alimento")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "a medida criada", response = Measure.class),
            @ApiResponse(code = 404, message = "alimento não encontrado")
    })
    public Response createMeasure(@PathParam("foodId") Long id, @ApiParam Measure measure) {
        if (!foodService.exists(id)) {
            logger.error("Food " + id + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        Food food = foodService.getById(id);
        measure.setFood(food);
        Measure createdMeasure = measureService.create(measure);

        URI createdURI = URI.create(uriInfo.getRequestUri() + "/" + createdMeasure.getId());
        return Response
                .created(createdURI)
                .entity(createdMeasure)
                .build();
    }


    @PUT
    @Path(FoodController.RESOURCE_URL + "/" + RESOURCE_URL + "/{id}")
    @ApiOperation(value = "atualiza uma medida")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "a medida atualizada", response = Measure.class),
            @ApiResponse(code = 404, message = "medida não encontrada")
    })
    public Response updateMeasure(@PathParam("id") Long id, @ApiParam Measure measure) {
        if (!measureService.exists(id)) {
            logger.error("Food " + id + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        measure.setId(id);
        Measure updatedMeasure = measureService.update(measure);

        return Response
                .ok()
                .entity(updatedMeasure)
                .build();
    }

    @Context
    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }
}