package com.ceres.domain.repository.taco;

import com.ceres.domain.entity.taco.Food;
import com.ceres.domain.entity.taco.FoodCategory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Leonardo on 19/01/2015.
 */
@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    Food findByName(String name);

    @Override
    @Cacheable("food-findAll")
    List<Food> findAll();

    @Cacheable("food-findByCategory")
    List<Food> findByCategory(FoodCategory category);
}
