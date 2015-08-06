package com.ceres.controller.v1.taco;

import com.ceres.controller.RestController;
import com.ceres.controller.v1.views.FoodView;
import com.ceres.domain.entity.taco.Food;
import com.ceres.service.taco.FoodService;
import com.wordnik.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Leonardo on 20/01/2015.
 */
@Named("FoodController")
@Singleton
@Path(RestController.V1)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Alimentos", basePath = "v1", description = "manipular alimentos", authorizations = {@Authorization(value = "PATIENT / NUTRITIONIST", type = "Basic")})
public final class FoodController {

    protected static final String RESOURCE_URL = "alimento";

    private FoodService foodService;

    private UriInfo uriInfo;

    private static final Logger logger = LoggerFactory.getLogger(FoodController.class);

    @Inject
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
        logger.info("FoodController ready...");
    }

    @GET
    @Path(RESOURCE_URL + "/{id}")
    @ApiOperation(value = "recuperar um alimento dado seu ID",
            notes = "Este serviço retorna a representação reduzida da entidade")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "o alimento", response = Map.class),
            @ApiResponse(code = 404, message = "alimento não encontrado")
    })
    public Response getById(@PathParam("id") Long id) {
        if (!foodService.exists(id)) {
            logger.error("Food " + id + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        Food food = foodService.getById(id);

        return Response.ok()
                .entity(new FoodView(food.getId(), food.getName()))
                .build();
    }

    @GET
    @Path(RESOURCE_URL)
    @ApiOperation(value = "recuperar todos os alimentos", responseContainer = "Map",
            notes = "Este serviço retorna a representação reduzida da entidade")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "todos os alimentos", response = Map.class)
    })
    public Response getAll() {

        List<Food> allFoods = foodService.getAll();

        return Response
                .ok()
                .entity(getSimplified(allFoods))
                .build();
    }

    @GET
    @Path(RESOURCE_URL + "/detalhado/{id}")
    @ApiOperation(value = "recuperar um alimento dado seu ID",
            notes = "Este serviço retorna a representação completa da entidade")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "o alimento", response = Food.class),
            @ApiResponse(code = 404, message = "alimento não encontrado")
    })
    public Response getByIdDetailed(@PathParam("id") Long id) {
        if (!foodService.exists(id)) {
            logger.error("Food " + id + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        Food food = foodService.getById(id);


        return Response.ok()
                .entity(food)
                .build();
    }


    @GET
    @Path(RESOURCE_URL + "/detalhado")
    @ApiOperation(value = "recuperar todos os alimentos", responseContainer = "Map",
            notes = "Este serviço retorna a representação completa da entidade")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "todos os alimentos", response = Food.class)
    })
    public Response getAllDetailed() {

        List<Food> allFoods = foodService.getAll();

        return Response
                .ok()
                .entity(allFoods)
                .build();
    }

    @Context
    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    private List<FoodView> getSimplified(List<Food> foods) {
        List<FoodView> foodViews = new ArrayList<>();

        foods.forEach(k -> foodViews.add(new FoodView(k.getId(), k.getName())));

        return foodViews;
    }
}