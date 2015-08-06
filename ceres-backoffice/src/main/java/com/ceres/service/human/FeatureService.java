package com.ceres.service.human;

import com.ceres.business.chart.ChartEntry;
import com.ceres.domain.entity.human.Feature;
import com.ceres.domain.entity.human.Patient;
import com.ceres.service.human.exception.FeatureException;

import java.time.LocalDate;
import java.util.List;
import java.util.SortedSet;

/**
 * Created by leonardo on 23/03/15.
 */
public interface FeatureService {

    void calculateFeatures(Patient patient);

    Feature getFeatureById(Long id);

    Feature getFeatureByIdWithoutCalculation(Long id);

    SortedSet<Feature> getPatientsFeatures(Patient p);

    SortedSet<Feature> getPatientsFeaturesByPeriod(LocalDate start, LocalDate end);

    List<ChartEntry> getUsualChartData(Patient p);

    List<ChartEntry> getFullChartData(Patient p);

    List<ChartEntry> getCalcChartData(Patient p);

    Feature createOrUpdate(Feature f) throws FeatureException;

    void deleteFeature(Feature f);

    boolean exists(Long id);
}
