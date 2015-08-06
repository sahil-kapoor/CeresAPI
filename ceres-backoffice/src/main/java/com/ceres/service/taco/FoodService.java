package com.ceres.service.taco;

import com.ceres.domain.entity.taco.Food;
import com.ceres.domain.entity.taco.FoodCategory;

import java.util.List;

/**
 * Created by leonardo on 08/03/15.
 */
public interface FoodService {
    Food getById(Long id);

    List<Food> getAll();

    List<Food> getByCategory(FoodCategory category);

    boolean exists(Long id);
}
