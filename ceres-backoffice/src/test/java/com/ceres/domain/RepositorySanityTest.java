package com.ceres.domain;

import com.ceres.domain.entity.taco.*;
import com.ceres.domain.repository.human.UserRepository;
import com.ceres.domain.repository.taco.CategoryRepository;
import com.ceres.domain.repository.taco.FoodRepository;
import com.ceres.domain.repository.taco.MeasureRepository;
import com.ceres.domain.repository.taco.NutriFactRepository;
import com.ceres.suites.integrated.AbstractDbEnvTestSuite;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Leonardo on 24/01/2015.
 */
public class RepositorySanityTest extends AbstractDbEnvTestSuite {

    @Inject
    FoodRepository foodRep;

    @Inject
    CategoryRepository categoryRep;

    @Inject
    NutriFactRepository nutriFactRep;

    @Inject
    UserRepository userRep;

    @Inject
    MeasureRepository measureRepository;

    @Before
    public void prepare() {
        FoodCategory foodFoodCategory = new FoodCategory("Grão");
        categoryRep.save(foodFoodCategory);

        NutriFact nutriFact = new NutriFact(NutriFactType.CHOLESTEROL, MeasureUnit.PCT);
        this.nutriFactRep.save(nutriFact);

        Food food = new FoodBuilder().setName("Arroz").setFoodCategory(foodFoodCategory).withDefaultPortion().build();
        food.addNutriFact(nutriFact, 2.32);

        food.addMeasure(new MeasureBuilder().setMeasure(new HomemadeMeasure("Fatia")).setPortionQuantity(2.5).build());
        food.addMeasure(new MeasureBuilder().setMeasure(new HomemadeMeasure("Xícara")).setPortionQuantity(1.5).build());

        foodRep.save(food);
    }

    @Test
    public void injectionTest() {
        assertNotNull(foodRep);
        assertNotNull(categoryRep);
        assertNotNull(nutriFactRep);
        assertNotNull(measureRepository);
        assertNotNull(userRep);
    }

    @Test
    public void findSanityTest() {
        assertNotNull(foodRep.findByName("Arroz"));

        assertNotNull(nutriFactRep.findByName("Colesterol"));

        assertNotNull(categoryRep.findByName("Grão"));
    }

    @Test
    public void deleteSanityTest() {
        Food food = foodRep.findByName("Arroz");
        foodRep.delete(food);
        assertFalse(foodRep.exists(food.getId()));

        NutriFact nutriFact = this.nutriFactRep.findByName("Colesterol");
        this.nutriFactRep.delete(nutriFact);
        assertFalse(this.nutriFactRep.exists(nutriFact.getId()));

        FoodCategory foodCategory = categoryRep.findByName("Grão");
        categoryRep.delete(foodCategory);
        assertFalse(categoryRep.exists(foodCategory.getId()));
    }

    @Test
    public void updateSanityTest() {
        NutriFact nutriFact = this.nutriFactRep.findByName("Colesterol");
        nutriFact.setName("Vitamina C");
        this.nutriFactRep.save(nutriFact);
        assertNotNull(this.nutriFactRep.findByName("Vitamina C"));

        FoodCategory foodCategory = categoryRep.findByName("Grão");
        foodCategory.setName("Industrializado");
        categoryRep.save(foodCategory);
        assertNotNull(categoryRep.findByName("Industrializado"));
    }


}
