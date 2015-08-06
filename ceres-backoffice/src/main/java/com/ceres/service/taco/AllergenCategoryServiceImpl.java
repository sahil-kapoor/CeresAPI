package com.ceres.service.taco;

import com.ceres.domain.entity.taco.AllergenCategory;
import com.ceres.domain.repository.taco.AllergenCategoryRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by leonardo on 31/03/15.
 */
@Service
public final class AllergenCategoryServiceImpl implements AllergenCategoryService {

    @Inject
    private AllergenCategoryRepository allergenCategoryRepository;

    @Override
    public AllergenCategory getById(Long id) {
        return allergenCategoryRepository.findOne(id);
    }

    @Override
    public List<AllergenCategory> getAll() {
        return allergenCategoryRepository.findAll();
    }

    @Override
    public boolean exists(Long id) {
        return allergenCategoryRepository.exists(id);
    }
}
