package com.ceres.domain.repository.taco;

import com.ceres.domain.entity.taco.Food;
import com.ceres.domain.entity.taco.Measure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by leonardo on 09/03/15.
 */
@Repository
public interface MeasureRepository extends JpaRepository<Measure, Long> {
    List<Measure> findByFood(Food food);
}
