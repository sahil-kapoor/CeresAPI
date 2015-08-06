package com.ceres.controller.v1.taco;

import com.ceres.controller.RestController;
import com.ceres.controller.v1.views.CategoryView;
import com.ceres.domain.entity.taco.AllergenCategory;
import com.ceres.domain.entity.taco.Food;
import com.ceres.domain.entity.taco.FoodCategory;
import com.ceres.service.taco.AllergenCategoryService;
import com.ceres.service.taco.FoodCategoryService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
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
import java.util.*;

/**
 * Created by leonardo on 01/02/15.
 */
@Named("CategoryController")
@Singleton
@Path(RestController.V1)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Alimentos", basePath = "v1", description = "manipular categoria de alimentos")
public final class CategoryController {

    protected static final String RESOURCE_URL_CATEGORY = "categoria";

    protected static final String RESOURCE_URL_ALLERGEN = "alergenico";

    private FoodCategoryService foodCategoryService;

    private UriInfo uriInfo;

    private AllergenCategoryService allergenCategoryService;

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Inject
    public CategoryController(FoodCategoryService foodCategoryService, AllergenCategoryService allergenCategoryService) {
        this.foodCategoryService = foodCategoryService;
        this.allergenCategoryService = allergenCategoryService;
        logger.info("CategoryController ready...");
    }

    @GET
    @Path(FoodController.RESOURCE_URL + "/" + RESOURCE_URL_CATEGORY + "/{id}")
    @ApiOperation(value = "recuperar uma categoria de alimento dado seu ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "a categoria", response = FoodCategory.class),
            @ApiResponse(code = 404, message = "categoria de alimento não encontrada")
    })
    public Response getFoodCategory(@PathParam("id") Long id) {
        if (!foodCategoryService.exists(id)) {
            logger.error("Food category " + id + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        FoodCategory foodCategory = foodCategoryService.getById(id);

        return Response.ok()
                .entity(getFoodsMap(foodCategory.getFoods()))
                .build();
    }

    @GET
    @Path(FoodController.RESOURCE_URL + "/" + RESOURCE_URL_ALLERGEN + "/{id}")
    @ApiOperation(value = "recuperar uma categoria de alergênico dado seu ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "a categoria", response = AllergenCategory.class),
            @ApiResponse(code = 404, message = "categoria de alergênico não encontrada")
    })
    public Response getAllergenCategory(@PathParam("id") Long id) {
        if (!allergenCategoryService.exists(id)) {
            logger.error("Allergen category " + id + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        AllergenCategory allergenCategory = allergenCategoryService.getById(id);

        return Response.ok()
                .entity(getFoodsMap(allergenCategory.getFoods()))
                .build();
    }


    @GET
    @Path(FoodController.RESOURCE_URL + "/" + RESOURCE_URL_CATEGORY)
    @ApiOperation(value = "recuperar todas as categorias de alimento")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "as categorias", response = FoodCategory.class)
    })
    public Response getAllFoodCategory() {

        List<FoodCategory> allCategories = foodCategoryService.getAll();

        List<CategoryView> categoryView = new ArrayList<>();

        allCategories.forEach(k -> {
            categoryView.add(new CategoryView(k.getId(), k.getName(), getFoodsMap(k.getFoods())));
        });

        return Response.ok()
                .entity(categoryView)
                .build();
    }

    @GET
    @Path(FoodController.RESOURCE_URL + "/" + RESOURCE_URL_ALLERGEN)
    @ApiOperation(value = "recuperar todas as categorias de alergênico")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "as categorias", response = AllergenCategory.class)
    })
    public Response getAllAllergensCategory() {

        List<AllergenCategory> allAllergens = allergenCategoryService.getAll();

        List<CategoryView> categoryView = new ArrayList<>();

        allAllergens.forEach(k -> {
            categoryView.add(new CategoryView(k.getId(), k.getName(), getFoodsMap(k.getFoods())));
        });

        return Response.ok()
                .entity(categoryView)
                .build();
    }

    @Context
    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    private Map<Long, String> getFoodsMap(Collection<Food> category) {
        Map<Long, String> foods = new HashMap<>();
        category.forEach(k -> foods.put(k.getId(), k.getName()));
        return foods;
    }

}
