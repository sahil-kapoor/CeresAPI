package com.ceres.domain.entity.menu;

import com.ceres.domain.Builder;
import com.ceres.domain.entity.taco.Food;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by leonardo on 14/03/15.
 */
public class MenuContentBuilder implements Builder<MenuContent> {

    private MealType mealType;
    private Menu menu;
    private Map<Food, Double> foods = new HashMap<>();

    public MenuContentBuilder setMealType(MealType mealType) {
        this.mealType = mealType;
        return this;
    }

    public MenuContentBuilder setMenu(Menu menu) {
        this.menu = menu;
        return this;
    }

    public MenuContentBuilder addFood(Food food, Double portion) {
        this.foods.put(food, portion);
        return this;
    }

    public MenuContentBuilder addAllFoods(Map<Food, Double> foods) {
        this.foods.putAll(foods);
        return this;
    }

    @Override
    public MenuContent build() {
        MenuContent menuContent = new MenuContent();
        menuContent.setMenu(menu);
        menuContent.setMealType(mealType);

        this.foods.forEach(menuContent::addFood);

        return menuContent;
    }
}
