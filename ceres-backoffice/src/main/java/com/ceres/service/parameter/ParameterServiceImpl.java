package com.ceres.service.parameter;

import com.ceres.domain.entity.parameter.Parameter;
import com.ceres.domain.repository.parameter.ParameterRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by leonardo on 22/03/15.
 */
@Service
public final class ParameterServiceImpl implements ParameterService {

    private ParameterRepository parameterRepository;

    @Inject
    public ParameterServiceImpl(ParameterRepository parameterRepository) {
        this.parameterRepository = parameterRepository;
    }

    @Override
    public Parameter getByMnemonic(String mnemonic) {
        return parameterRepository.findByMnemonic(mnemonic);
    }

    @Override
    public Parameter getById(Long id) {
        return parameterRepository.findOne(id);
    }

    @Override
    public List<Parameter> getAll() {
        return parameterRepository.findAll();
    }

    @Override
    public boolean exists(Long id) {
        return parameterRepository.exists(id);
    }

    @Override
    public Parameter createOrUpdate(Parameter param) {
        validateParameter(param);
        return parameterRepository.save(param);
    }

    private void validateParameter(Parameter param) {
        // TODO
    }

    @Override
    public void delete(Long id) {
        Parameter p = parameterRepository.findOne(id);
        parameterRepository.delete(p);
    }

    @Override
    public boolean existByMnemonic(String mne) {
        return parameterRepository.countByMnemonic(mne) > 0;
    }


}
