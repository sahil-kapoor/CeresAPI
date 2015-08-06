package com.ceres.domain.entity.taco;

import com.ceres.domain.Builder;
import com.ceres.domain.embedabble.Portion;
import com.ceres.domain.entity.human.Feature;

import java.util.HashSet;
import java.util.Set;

public class FoodBuilder implements Builder<Food> {

    private String name;
    private FoodCategory foodCategory;
    private Portion portion;
    private Set<Feature> features = new HashSet<>();
    private Long id = null;

    public FoodBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public FoodBuilder setFoodCategory(FoodCategory foodCategory) {
        this.foodCategory = foodCategory;
        return this;
    }

    public FoodBuilder setPortion(Portion portion) {
        this.portion = portion;
        return this;
    }

    public FoodBuilder withDefaultPortion() {
        portion = new Portion();
        portion.setMeasureUnit(MeasureUnit.G);
        portion.setValue(100.0);
        return this;
    }

    public FoodBuilder setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public Food build() {
        Food food = new Food(name, foodCategory, portion);
        food.setId(id);
        return food;
    }

}