package com.ceres.service.taco;

import com.ceres.domain.entity.taco.FoodCategory;

import java.util.List;

/**
 * Created by leonardo on 08/03/15.
 */
public interface FoodCategoryService {
    FoodCategory getById(Long id);

    List<FoodCategory> getAll();

    boolean exists(Long id);
}
