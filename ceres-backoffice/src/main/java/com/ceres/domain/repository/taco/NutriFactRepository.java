package com.ceres.domain.repository.taco;

import com.ceres.domain.entity.taco.NutriFact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Leonardo on 19/01/2015.
 */
public interface NutriFactRepository extends JpaRepository<NutriFact, Long> {

    NutriFact findByName(String name);

    Page<NutriFact> findAll(Pageable pageable);

    List<NutriFact> findByNameContaining(String name);

}
