package com.ceres.service.taco;

import com.ceres.domain.entity.taco.Food;
import com.ceres.domain.entity.taco.Measure;

import java.util.List;

/**
 * Created by leonardo on 30/04/15.
 */
public interface MeasureService {

    Measure getById(Long id);

    List<Measure> getByFood(Food food);

    Measure create(Measure measure);

    Measure update(Measure measure);

    boolean exists(Long id);
}
