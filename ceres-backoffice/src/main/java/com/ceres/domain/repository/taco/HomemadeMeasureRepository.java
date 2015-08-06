package com.ceres.domain.repository.taco;

import com.ceres.domain.entity.taco.HomemadeMeasure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by leonardo on 30/04/15.
 */
@Repository
public interface HomemadeMeasureRepository extends JpaRepository<HomemadeMeasure, Long> {
}
