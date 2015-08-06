package com.ceres.service.taco;

import com.ceres.domain.entity.taco.Food;
import com.ceres.domain.entity.taco.Measure;
import com.ceres.domain.repository.taco.MeasureRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by leonardo on 30/04/15.
 */
@Service
public class MeasureServiceImpl implements MeasureService {

    private MeasureRepository measureRepository;

    private FoodService foodService;

    @Inject
    public MeasureServiceImpl(MeasureRepository measureRepository, FoodService foodService) {
        this.measureRepository = measureRepository;
        this.foodService = foodService;
    }

    @Override
    public Measure getById(Long id) {
        return measureRepository.findOne(id);
    }

    @Override
    public List<Measure> getByFood(Food food) {
        return measureRepository.findByFood(food);
    }

    @Override
    public Measure create(Measure measure) {
        return measureRepository.save(measure);
    }

    @Override
    public Measure update(Measure measure) {
        return measureRepository.save(measure);
    }

    @Override
    public boolean exists(Long id) {
        return measureRepository.exists(id);
    }
}
