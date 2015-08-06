package com.ceres.controller.v1.util;

import com.ceres.controller.RestController;
import com.ceres.domain.entity.human.ActivityRate;
import com.ceres.domain.entity.menu.MealType;
import com.ceres.domain.entity.parameter.ParameterType;
import com.ceres.domain.entity.taco.MeasureUnit;
import com.ceres.domain.entity.taco.NutriFactType;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.EnumSet;
import java.util.Set;

/**
 * Created by leonardo on 19/04/15.
 */
@Named("TypesController")
@Singleton
@Path(RestController.V1)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Tipos de dados (Enums)", basePath = "v1", description = "tipos de dados do sistema", produces = MediaType.APPLICATION_JSON)
public final class TypesController {

    private static final String RESOURCE_URL = "tipo";

    private UriInfo uriInfo;

    @GET
    @Path(RESOURCE_URL + "/refeicao")
    @ApiOperation(value = "tipos de refeição")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "todos os tipos de refeição", response = String.class)

    })
    public Response getMealTypes() {
        Set<MealType> mealTypes = EnumSet.allOf(MealType.class);
        return Response.ok(mealTypes).build();
    }

    @GET
    @Path(RESOURCE_URL + "/info-nutricional")
    @ApiOperation(value = "tipos de refeição")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "todos as informações nutricionais", response = String.class)
    })
    public Response getNutrifacts() {
        Set<NutriFactType> nutriFactTypes = EnumSet.allOf(NutriFactType.class);
        return Response.ok(nutriFactTypes).build();
    }

    @GET
    @Path(RESOURCE_URL + "/medida")
    @ApiOperation(value = "tipos de medida")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "todos os tipos de medida", response = String.class)
    })
    public Response getParameterTypes() {
        Set<ParameterType> parameterTypes = EnumSet.allOf(ParameterType.class);
        return Response.ok(parameterTypes).build();
    }

    @GET
    @Path(RESOURCE_URL + "/unidade-de-medida")
    @ApiOperation(value = "tipos de unidade de medida")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "todos os tipos de unidade de medida", response = String.class)
    })
    public Response getMeasureUnits() {
        Set<MeasureUnit> measureUnits = EnumSet.allOf(MeasureUnit.class);
        return Response.ok(measureUnits).build();
    }

    @GET
    @Path(RESOURCE_URL + "/taxa-de-atividade")
    @ApiOperation(value = "tipos as taxas de atividade")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "todos as taxas de atividades", response = String.class)
    })
    public Response getActivityRates() {
        Set<ActivityRate> activityRates = EnumSet.allOf(ActivityRate.class);
        return Response.ok(activityRates).build();
    }

    @Context
    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }
}
