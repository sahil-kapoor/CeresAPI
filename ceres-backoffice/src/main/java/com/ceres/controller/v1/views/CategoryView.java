package com.ceres.controller.v1.views;

import java.util.Map;

/**
 * Created by leonardo on 23/04/15.
 */
public class CategoryView {

    private Long id;

    private String name;

    private Map<Long, String> foods;

    public CategoryView(Long id, String name, Map<Long, String> foodsMap) {
        this.id = id;
        this.name = name;
        this.foods = foodsMap;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Long, String> getFoods() {
        return foods;
    }

    public void setFoods(Map<Long, String> foods) {
        this.foods = foods;
    }
}
