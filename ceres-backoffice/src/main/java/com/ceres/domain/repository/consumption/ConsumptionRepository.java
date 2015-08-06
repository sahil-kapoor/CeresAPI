package com.ceres.domain.repository.consumption;

import com.ceres.domain.entity.consumption.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by leonardo on 25/04/15.
 */
@Repository
public interface ConsumptionRepository extends JpaRepository<Consumption, Long> {
}
