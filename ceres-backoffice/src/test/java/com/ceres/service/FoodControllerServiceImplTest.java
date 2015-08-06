package com.ceres.service;

import com.ceres.service.taco.FoodService;
import com.ceres.suites.integrated.AbstractDbEnvTestSuite;
import org.junit.Test;

import javax.inject.Inject;

import static org.junit.Assert.assertTrue;

/**
 * Created by Leonardo on 25/01/2015.
 */
public class FoodControllerServiceImplTest extends AbstractDbEnvTestSuite {

    @Inject
    FoodService foodService;

    @Test
    public void shouldReturnAllFoods() {

        assertTrue(true);

    }


}
