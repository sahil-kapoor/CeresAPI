package com.ceres.domain.repository.taco;

import com.ceres.domain.entity.taco.AllergenCategory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by leonardo on 31/03/15.
 */
public interface AllergenCategoryRepository extends JpaRepository<AllergenCategory, Long> {

    @Override
    @Cacheable("allergen-findall")
    List<AllergenCategory> findAll();
}
