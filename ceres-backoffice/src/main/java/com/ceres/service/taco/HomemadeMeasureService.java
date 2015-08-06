package com.ceres.service.taco;

import com.ceres.domain.entity.taco.HomemadeMeasure;

import java.util.List;

/**
 * Created by leonardo on 30/04/15.
 */
public interface HomemadeMeasureService {

    HomemadeMeasure create(HomemadeMeasure homemadeMeasure);

    HomemadeMeasure update(HomemadeMeasure homemadeMeasure);

    HomemadeMeasure getById(Long id);

    List<HomemadeMeasure> getAll();

    boolean exists(Long id);
}
