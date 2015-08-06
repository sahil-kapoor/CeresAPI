package com.ceres.service.taco;

import com.ceres.domain.entity.taco.AllergenCategory;

import java.util.List;

/**
 * Created by leonardo on 31/03/15.
 */
public interface AllergenCategoryService {

    AllergenCategory getById(Long id);

    List<AllergenCategory> getAll();

    boolean exists(Long id);
}
