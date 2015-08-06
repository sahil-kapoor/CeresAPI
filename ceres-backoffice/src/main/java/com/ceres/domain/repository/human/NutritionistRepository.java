package com.ceres.domain.repository.human;

import com.ceres.domain.entity.human.Nutritionist;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by leonardo on 17/03/15.
 */
public interface NutritionistRepository extends JpaRepository<Nutritionist, Long> {

    Nutritionist findByCrn(String crn);

}
