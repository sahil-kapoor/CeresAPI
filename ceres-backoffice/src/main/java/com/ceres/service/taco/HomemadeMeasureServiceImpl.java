package com.ceres.service.taco;

import com.ceres.domain.entity.taco.HomemadeMeasure;
import com.ceres.domain.repository.taco.HomemadeMeasureRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by leonardo on 30/04/15.
 */
@Service
public class HomemadeMeasureServiceImpl implements HomemadeMeasureService {

    private HomemadeMeasureRepository homemadeMeasureRepository;

    @Inject
    public HomemadeMeasureServiceImpl(HomemadeMeasureRepository homemadeMeasureRepository) {
        this.homemadeMeasureRepository = homemadeMeasureRepository;
    }

    @Override
    public HomemadeMeasure create(HomemadeMeasure homemadeMeasure) {
        return homemadeMeasureRepository.save(homemadeMeasure);
    }

    @Override
    public HomemadeMeasure update(HomemadeMeasure homemadeMeasure) {
        return homemadeMeasureRepository.save(homemadeMeasure);
    }

    @Override
    public HomemadeMeasure getById(Long id) {
        return homemadeMeasureRepository.findOne(id);

    }

    @Override
    public List<HomemadeMeasure> getAll() {
        return homemadeMeasureRepository.findAll();
    }

    @Override
    public boolean exists(Long id) {
        return homemadeMeasureRepository.exists(id);
    }
}
