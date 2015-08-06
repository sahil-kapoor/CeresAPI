package com.ceres.service.parameter;

import com.ceres.domain.entity.human.Feature;
import com.ceres.domain.entity.parameter.Parameter;
import com.ceres.domain.entity.parameter.ParameterValue;

import java.util.Collection;
import java.util.List;

/**
 * Created by leonardo on 16/04/15.
 */
public interface ParameterValueService {

    ParameterValue resolveParameterValue(Parameter param, Feature feature, Object value);

    List<ParameterValue> getAll();

    ParameterValue getById(Long id);

    void createOrUpdate(ParameterValue parameterValue);

    boolean exists(Long id);

    List<ParameterValue> getByFeature(Feature feature);

    void remove(Long id);

    void removeAll(Collection<ParameterValue> values);
}
