package com.ceres.controller.health;

import com.ceres.controller.v1.taco.CategoryController;
import com.ceres.domain.embedabble.Portion;
import com.ceres.domain.entity.taco.*;
import com.ceres.domain.repository.taco.AllergenCategoryRepository;
import com.ceres.domain.repository.taco.CategoryRepository;
import com.ceres.domain.repository.taco.FoodRepository;
import com.ceres.suites.integrated.AbstractDbEnvTestSuite;
import org.glassfish.jersey.server.internal.routing.UriRoutingContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

/**
 * Created by leonardo on 18/04/15.
 */
public class CategoryControllerHealth extends AbstractDbEnvTestSuite {

    @Inject
    CategoryController categoryController;

    @Inject
    FoodRepository foodRepository;

    @Inject
    CategoryRepository categoryRepository;

    @Inject
    AllergenCategoryRepository allergenCategoryRepository;

    FoodCategory cat;

    AllergenCategory ale;

    @Before
    public void before() {
        categoryController.setUriInfo(new UriRoutingContext(getFakeContainerRequest()));

        cat = categoryRepository.save(new FoodCategory("common"));

        Food food = foodRepository.save(new FoodBuilder()
                .setFoodCategory(cat)
                .setName("Batata")
                .setPortion(new Portion(2.00, MeasureUnit.G))
                .build());

        Food allergen = foodRepository.save(new FoodBuilder()
                .setFoodCategory(cat)
                .setName("Alho")
                .setPortion(new Portion(2.00, MeasureUnit.G))
                .build());

        ale = new AllergenCategory("allergen");
        ale.addFood(allergen);

        ale = allergenCategoryRepository.save(ale);

    }

    @Test
    public void categoryControllerHealth() {
        Response categoryResponse;

        categoryResponse = categoryController.getAllAllergensCategory();
        assertEquals(Response.Status.OK, categoryResponse.getStatusInfo());

        categoryResponse = categoryController.getAllergenCategory(ale.getId());
        assertEquals(Response.Status.OK, categoryResponse.getStatusInfo());

        categoryResponse = categoryController.getAllFoodCategory();
        assertEquals(Response.Status.OK, categoryResponse.getStatusInfo());

        categoryResponse = categoryController.getFoodCategory(cat.getId());
        assertEquals(Response.Status.OK, categoryResponse.getStatusInfo());

    }

    @After
    public void cleanup() {
        ale.getFoods().clear();
        allergenCategoryRepository.delete(ale);
    }

}
