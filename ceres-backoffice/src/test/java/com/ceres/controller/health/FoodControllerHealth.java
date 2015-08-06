package com.ceres.controller.health;

import com.ceres.controller.v1.taco.FoodController;
import com.ceres.domain.embedabble.Portion;
import com.ceres.domain.entity.taco.Food;
import com.ceres.domain.entity.taco.FoodBuilder;
import com.ceres.domain.entity.taco.FoodCategory;
import com.ceres.domain.entity.taco.MeasureUnit;
import com.ceres.domain.repository.taco.CategoryRepository;
import com.ceres.domain.repository.taco.FoodRepository;
import com.ceres.suites.integrated.AbstractDbEnvTestSuite;
import org.glassfish.jersey.server.internal.routing.UriRoutingContext;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

/**
 * Created by leonardo on 18/04/15.
 */
public class FoodControllerHealth extends AbstractDbEnvTestSuite {

    @Inject
    FoodController foodController;

    @Inject
    FoodRepository foodRepository;

    @Inject
    CategoryRepository categoryRepository;

    Food food;

    @Before
    public void before() {

        foodController.setUriInfo(new UriRoutingContext(getFakeContainerRequest()));

        FoodCategory cat = categoryRepository.save(new FoodCategory("Test"));

        food = foodRepository.save(new FoodBuilder()
                .setFoodCategory(cat)
                .setName("Pão")
                .setPortion(new Portion(2.00, MeasureUnit.G))
                .build());

    }

    @Test
    public void foodControllerHealth() {
        Response userResponse;

        userResponse = foodController.getAll();
        assertEquals(Response.Status.OK, userResponse.getStatusInfo());

        userResponse = foodController.getById(food.getId());
        assertEquals(Response.Status.OK, userResponse.getStatusInfo());
    }

}
