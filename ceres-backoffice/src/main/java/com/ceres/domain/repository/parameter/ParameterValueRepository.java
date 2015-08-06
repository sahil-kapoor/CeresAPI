package com.ceres.domain.repository.parameter;

import com.ceres.domain.entity.human.Feature;
import com.ceres.domain.entity.parameter.Parameter;
import com.ceres.domain.entity.parameter.ParameterValue;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by leonardo on 21/03/15.
 */
public interface ParameterValueRepository extends JpaRepository<ParameterValue, Long> {

    ParameterValue findByParameterAndFeature(Parameter param, Feature feature);

    int countByParameterAndFeature(Parameter param, Feature feature);
}
