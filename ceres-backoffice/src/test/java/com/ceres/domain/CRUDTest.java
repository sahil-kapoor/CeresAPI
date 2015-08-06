package com.ceres.domain;

import com.ceres.domain.entity.taco.*;
import com.ceres.domain.repository.taco.CategoryRepository;
import com.ceres.domain.repository.taco.FoodRepository;
import com.ceres.domain.repository.taco.NutriFactRepository;
import com.ceres.suites.integrated.AbstractDbEnvTestSuite;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.dao.DataIntegrityViolationException;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Leonardo on 27/01/2015.
 */
public class CRUDTest extends AbstractDbEnvTestSuite {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Inject
    FoodRepository foodRep;

    @Inject
    CategoryRepository categoryRep;

    @Inject
    NutriFactRepository featureRep;


    FoodCategory foodCategory;

    NutriFact nutriFact;

    Food food;


    @Test
    public void shouldPersistFeature() {
        nutriFact = new NutriFact(NutriFactType.CHOLESTEROL, MeasureUnit.KCAL);
        featureRep.save(nutriFact);

        nutriFact = featureRep.findByName("Colesterol");

        assertNotNull(nutriFact);
        assertNotNull(nutriFact.getName());
        assertNotNull(nutriFact.getMeasureUnit());
    }

    @Test
    public void shouldPersistCategory() {
        foodCategory = new FoodCategory("Grão");
        categoryRep.save(foodCategory);

        foodCategory = categoryRep.findByName("Grão");

        assertNotNull(foodCategory);
        assertNotNull(foodCategory.getName());
    }

    @Test
    public void shouldPersistFood() {
        foodCategory = new FoodCategory("Grão");
        categoryRep.save(foodCategory);
        foodCategory = categoryRep.findByName("Grão");

        nutriFact = new NutriFact(NutriFactType.CHOLESTEROL, MeasureUnit.MG);
        featureRep.save(nutriFact);
        nutriFact = featureRep.findByName("Colesterol");

        food = new FoodBuilder()
                .setName("Arroz")
                .setFoodCategory(foodCategory)
                .withDefaultPortion()
                .build();

        food.addNutriFact(nutriFact, 2.22);

        food.addMeasure(new MeasureBuilder()
                .setMeasure(new HomemadeMeasure("Fatia"))
                .setPortionQuantity(2.22)
                .build());
        food.addMeasure(new MeasureBuilder()
                .setMeasure(new HomemadeMeasure("Xícara"))
                .setPortionQuantity(3.00)
                .build());

        foodRep.save(food);

        food = foodRep.findByName("Arroz");

        assertNotNull(food);
        assertEquals(food.getName(), "Arroz");
        assertEquals(food.getNutriFacts().size(), 1);
        assertEquals(food.getCategory().getName(), "Grão");
        assertEquals(food.getHomemadeMeasureValues().size(), 2);
    }

    @Test
    public void shouldThrowErrorWithNullField() {
        food = new FoodBuilder()
                .setName("Arroz")
                .setFoodCategory(foodCategory)
                .withDefaultPortion()
                .build();

        exception.expect(DataIntegrityViolationException.class);
        foodRep.save(food);

        food = new FoodBuilder().setName("Arroz, Cru").setFoodCategory(null).build();
        food.addNutriFact(nutriFact, 2.22);

        exception.expect(DataIntegrityViolationException.class);
        foodRep.save(food);

        foodCategory = new FoodCategory(null);

        exception.expect(DataIntegrityViolationException.class);
        categoryRep.save(foodCategory);

        nutriFact = new NutriFact(NutriFactType.CHOLESTEROL, null);

        exception.expect(DataIntegrityViolationException.class);
        featureRep.save(nutriFact);
    }

}
