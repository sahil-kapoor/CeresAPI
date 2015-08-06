package com.ceres.domain.repository.human;

import com.ceres.domain.entity.human.Feature;
import com.ceres.domain.entity.human.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.SortedSet;

/**
 * Created by leonardo on 12/03/15.
 */
@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {

    Feature findByVisitDate(LocalDate visitDate);

    SortedSet<Feature> findByVisitDateBetween(LocalDate start, LocalDate end);

    SortedSet<Feature> findByPatient(Patient p);
}
