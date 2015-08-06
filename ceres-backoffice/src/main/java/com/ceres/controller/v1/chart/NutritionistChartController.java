package com.ceres.controller.v1.chart;

import com.ceres.controller.RestController;
import com.ceres.service.human.NutritionistService;
import com.wordnik.swagger.annotations.Api;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by leonardo on 03/05/15.
 */
@Named("NutritionistChartController")
@Path(RestController.V1)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Gráficos do Nutricionista", basePath = "v1")
public class NutritionistChartController {

    private NutritionistService nutritionistService;

    @Inject
    public NutritionistChartController(NutritionistService nutritionistService) {
        this.nutritionistService = nutritionistService;
    }

}
