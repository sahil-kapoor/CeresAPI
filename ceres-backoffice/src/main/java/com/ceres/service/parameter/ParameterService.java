package com.ceres.service.parameter;

import com.ceres.domain.entity.parameter.Parameter;

import java.util.List;

/**
 * Created by leonardo on 22/03/15.
 */
public interface ParameterService {

    Parameter getByMnemonic(String mnemonic);

    Parameter getById(Long id);

    List<Parameter> getAll();

    boolean exists(Long id);

    Parameter createOrUpdate(Parameter param);

    void delete(Long id);

    boolean existByMnemonic(String mne);
}
