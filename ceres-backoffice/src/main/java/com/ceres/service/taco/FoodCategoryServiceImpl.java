package com.ceres.service.taco;

import com.ceres.domain.entity.taco.FoodCategory;
import com.ceres.domain.repository.taco.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by leonardo on 08/03/15.
 */
@Service
public final class FoodCategoryServiceImpl implements FoodCategoryService {

    private CategoryRepository categoryRepository;

    @Override
    public FoodCategory getById(Long id) {
        if (!categoryRepository.exists(id)) {
            // TODO
        }
        return categoryRepository.findOne(id);
    }

    @Override
    public List<FoodCategory> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public boolean exists(Long id) {
        return categoryRepository.exists(id);
    }

    @Inject
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
}
