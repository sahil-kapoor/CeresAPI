package com.ceres.service.parameter;

import com.ceres.domain.entity.human.Feature;
import com.ceres.domain.entity.parameter.Parameter;
import com.ceres.domain.entity.parameter.ParameterValue;
import com.ceres.domain.repository.parameter.ParameterValueRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

import static com.ceres.service.parameter.helper.ParameterValueHelper.cloneWithNewValue;
import static com.ceres.service.parameter.helper.ParameterValueHelper.createParameterValue;

/**
 * Created by leonardo on 16/04/15.
 */
@Service
public class ParameterValueServiceImpl implements ParameterValueService {

    private ParameterValueRepository parameterValueRepository;

    @Inject
    public ParameterValueServiceImpl(ParameterValueRepository parameterValueRepository) {
        this.parameterValueRepository = parameterValueRepository;
    }

    @Override
    public ParameterValue resolveParameterValue(Parameter param, Feature feature, Object value) {
        if (feature.getId() != null && parameterValueRepository.countByParameterAndFeature(param, feature) > 0) {
            return cloneWithNewValue(parameterValueRepository.findByParameterAndFeature(param, feature), value);
        }
        return createParameterValue(param, feature, value);
    }

    @Override
    public List<ParameterValue> getAll() {
        return null;
    }

    @Override
    public ParameterValue getById(Long id) {
        return null;
    }

    @Override
    public void createOrUpdate(ParameterValue parameterValue) {

    }

    @Override
    public boolean exists(Long id) {
        return false;
    }

    @Override
    public List<ParameterValue> getByFeature(Feature feature) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void removeAll(Collection<ParameterValue> values) {
        parameterValueRepository.deleteInBatch(values);
    }
}
