package com.ceres.controller.v1.taco;

import com.ceres.controller.RestController;
import com.ceres.domain.entity.taco.Food;
import com.ceres.domain.entity.taco.HomemadeMeasure;
import com.ceres.service.taco.FoodService;
import com.ceres.service.taco.HomemadeMeasureService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Leonardo on 20/01/2015.
 */
@Named("HomemadeMeasureController")
@Singleton
@Path(RestController.V1)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Medidas Caseiras", basePath = "v1", description = "manipular medidas caseiras")
public final class HomemadeMeasureController {

    protected static final String RESOURCE_URL = "medida-caseira";

    private FoodService foodService;

    private HomemadeMeasureService homemadeMeasureService;

    private UriInfo uriInfo;

    private static final Logger logger = LoggerFactory.getLogger(HomemadeMeasureController.class);

    @Inject
    public HomemadeMeasureController(FoodService foodService, HomemadeMeasureService homemadeMeasureService) {
        this.homemadeMeasureService = homemadeMeasureService;
        this.foodService = foodService;
        logger.info("HomemadeMeasureController ready...");
    }

    @GET
    @Path(RESOURCE_URL + "/{id}")
    @ApiOperation(value = "recupera uma medida caseira")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "a medida", response = HomemadeMeasure.class),
            @ApiResponse(code = 404, message = "medida não encontrada")
    })
    public Response getById(@PathParam("id") Long id) {
        if (!homemadeMeasureService.exists(id)) {
            logger.error("HomemadeMeasure " + id + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        HomemadeMeasure homemadeMeasure = homemadeMeasureService.getById(id);

        return Response.ok()
                .entity(homemadeMeasure)
                .build();
    }

    @GET
    @Path(RESOURCE_URL)
    @ApiOperation(value = "recuperar todas as medidas caseiras")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "as medidas", response = HomemadeMeasure.class),
    })
    public Response getAllHomemadeMeasures() {

        List<HomemadeMeasure> homemadeMeasures = homemadeMeasureService.getAll();


        return Response.ok()
                .entity(homemadeMeasures)
                .build();
    }


    @POST
    @Path(RESOURCE_URL)
    @ApiOperation(value = "cria uma medida caseira")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "medida caseira criada", response = HomemadeMeasure.class),
            @ApiResponse(code = 500, message = "erro ao criar medida caseira")
    })
    public Response createHomemadeMeasure(@ApiParam HomemadeMeasure homemadeMeasure) {

        HomemadeMeasure homemadeM = homemadeMeasureService.create(homemadeMeasure);

        URI createdURI = URI.create(uriInfo.getRequestUri() + "/" + homemadeM.getId());
        return Response
                .created(createdURI)
                .entity(homemadeM)
                .build();
    }

    @PUT
    @Path(RESOURCE_URL + "/{id}")
    @ApiOperation(value = "recuperar todas as medidas caseiras")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "todos as medidas caseiras", response = HomemadeMeasure.class)
    })
    public Response updateHomemadeMeasure(@PathParam("id") Long id, @ApiParam HomemadeMeasure homemadeMeasure) {
        if (!homemadeMeasureService.exists(id)) {
            logger.error("HomemadeMeasure " + id + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        homemadeMeasure.setId(id);
        HomemadeMeasure homemadeM = homemadeMeasureService.create(homemadeMeasure);


        return Response
                .ok()
                .entity(homemadeM)
                .build();
    }

    @Context
    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    private Map<Long, String> getSimplified(List<Food> foods) {
        Map<Long, String> simplifiedFoods = new HashMap<>();

        foods.forEach(k -> simplifiedFoods.put(k.getId(), k.getName()));

        return simplifiedFoods;
    }
}