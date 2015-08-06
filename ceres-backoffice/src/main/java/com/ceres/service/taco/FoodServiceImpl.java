package com.ceres.service.taco;

import com.ceres.domain.entity.taco.Food;
import com.ceres.domain.entity.taco.FoodCategory;
import com.ceres.domain.repository.taco.FoodRepository;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by leonardo on 08/03/15.
 */
@Service("FoodService")
@EnableCaching
public final class FoodServiceImpl implements FoodService {

    private FoodRepository foodRepository;

    @Override
    public List<Food> getAll() {
        return foodRepository.findAll();
    }

    @Override
    public List<Food> getByCategory(FoodCategory category) {
        return foodRepository.findByCategory(category);
    }

    @Override
    public boolean exists(Long id) {
        return foodRepository.exists(id);
    }

    @Override
    public Food getById(Long id) {
        if (!foodRepository.exists(id)) {
//        throw new FoodException(); TODO
        }
        return foodRepository.findOne(id);
    }

    @Inject
    public void setFoodRepository(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

}
