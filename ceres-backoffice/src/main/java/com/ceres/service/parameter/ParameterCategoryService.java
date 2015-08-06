package com.ceres.service.parameter;

import com.ceres.domain.entity.parameter.ParameterCategory;

import java.util.List;

/**
 * Created by leonardo on 22/03/15.
 */
public interface ParameterCategoryService {
    List<ParameterCategory> getAll();

    boolean exists(Long categoryId);

    ParameterCategory getById(Long categoryId);

    ParameterCategory createOrUpdate(ParameterCategory parameter);

    void delete(Long parameterId);
}
