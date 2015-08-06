package com.ceres.domain.repository.parameter;

import com.ceres.domain.entity.parameter.ParameterCategory;
import com.ceres.domain.repository.NamedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by leonardo on 21/03/15.
 */
public interface ParameterCategoryRepository extends JpaRepository<ParameterCategory, Long>, NamedEntity<ParameterCategory> {
}
