package com.ceres.service.parameter;

import com.ceres.domain.entity.parameter.ParameterCategory;
import com.ceres.domain.repository.parameter.ParameterCategoryRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by leonardo on 22/03/15.
 */
@Service
public final class ParameterCategoryServiceImpl implements ParameterCategoryService {

    private ParameterCategoryRepository parameterCategoryRepository;

    @Inject
    public ParameterCategoryServiceImpl(ParameterCategoryRepository parameterCategoryRepository) {
        this.parameterCategoryRepository = parameterCategoryRepository;
    }

    @Override
    public List<ParameterCategory> getAll() {
        return parameterCategoryRepository.findAll();
    }

    @Override
    public boolean exists(Long categoryId) {
        return parameterCategoryRepository.exists(categoryId);
    }

    @Override
    public ParameterCategory getById(Long categoryId) {
        return parameterCategoryRepository.findOne(categoryId);
    }

    @Override
    public ParameterCategory createOrUpdate(ParameterCategory parameter) {
        return parameterCategoryRepository.save(parameter);
    }

    @Override
    public void delete(Long parameterId) {
        parameterCategoryRepository.delete(parameterId);
    }
}
