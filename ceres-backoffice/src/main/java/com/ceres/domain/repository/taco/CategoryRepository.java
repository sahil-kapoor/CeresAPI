package com.ceres.domain.repository.taco;

import com.ceres.domain.entity.taco.FoodCategory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Leonardo on 19/01/2015.
 */
public interface CategoryRepository extends JpaRepository<FoodCategory, Long> {

    FoodCategory findByName(String name);

    @Override
    @Cacheable("category-findall")
    List<FoodCategory> findAll();

    List<FoodCategory> findByNameContaining(String name);


}
