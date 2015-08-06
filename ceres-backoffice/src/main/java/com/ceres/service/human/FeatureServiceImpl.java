package com.ceres.service.human;

import com.ceres.business.calculation.Calculator;
import com.ceres.business.chart.ChartEntry;
import com.ceres.business.chart.MapChartEntry;
import com.ceres.domain.entity.human.Feature;
import com.ceres.domain.entity.human.Patient;
import com.ceres.domain.entity.parameter.Parameter;
import com.ceres.domain.entity.parameter.ParameterValue;
import com.ceres.domain.repository.human.FeatureRepository;
import com.ceres.service.human.exception.FeatureException;
import com.ceres.service.human.exception.FeatureExceptionMessages;
import com.ceres.service.parameter.ParameterService;
import com.ceres.service.parameter.ParameterValueService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by leonardo on 23/03/15.
 */
@Service(value = "FeatureService")
public final class FeatureServiceImpl implements FeatureService {

    private FeatureRepository featureRepository;

    private ParameterService parameterService;

    private ParameterValueService parameterValueService;

    private Calculator<Feature> calculator;

    @Inject
    public FeatureServiceImpl(FeatureRepository featureRepository, ParameterService parameterService, ParameterValueService parameterValueService, Calculator<Feature> calculator) {
        this.featureRepository = featureRepository;
        this.parameterService = parameterService;
        this.parameterValueService = parameterValueService;
        this.calculator = calculator;
    }

    @Override
    public void calculateFeatures(Patient patient) {
        this.calculate(patient.getFeatures());
    }

    @Override
    public Feature getFeatureById(Long id) {
        Feature f = featureRepository.findOne(id);
        this.calculate(f);
        return f;
    }

    @Override
    public Feature getFeatureByIdWithoutCalculation(Long id) {
        return featureRepository.findOne(id);
    }

    @Override
    public SortedSet<Feature> getPatientsFeatures(Patient p) {
        SortedSet<Feature> features = featureRepository.findByPatient(p);
        this.calculate(features);
        return features;
    }

    @Override
    public SortedSet<Feature> getPatientsFeaturesByPeriod(LocalDate start, LocalDate end) {
        SortedSet<Feature> features = featureRepository.findByVisitDateBetween(start, end);
        this.calculate(features);
        return features;
    }

    @Override
    public List<ChartEntry> getUsualChartData(Patient p) {
        SortedSet<Feature> features = featureRepository.findByPatient(p);
        this.calculate(features);

        List<ChartEntry> chartData = new ArrayList<>();

        features.forEach(k -> {
            Map<String, Object> entry = new HashMap<String, Object>();
            entry.putAll(k.getFeatureParameters());

            MapChartEntry chartEntry = new MapChartEntry<String, Object>();
            chartEntry.setData(entry);
            chartEntry.setLabel(k.getVisitDate());

            chartData.add(chartEntry);

        });

        return chartData;
    }

    @Override
    public List<ChartEntry> getFullChartData(Patient p) {
        SortedSet<Feature> features = featureRepository.findByPatient(p);
        this.calculate(features);

        List<ChartEntry> chartData = new ArrayList<>();

        features.forEach(k -> {
            Map<String, Object> entry = new HashMap<String, Object>();
            entry.putAll(k.getFeatureParameters());
            entry.putAll(k.getCalcResults());

            MapChartEntry chartEntry = new MapChartEntry<String, Object>();
            chartEntry.setData(entry);
            chartEntry.setLabel(k.getVisitDate());

            chartData.add(chartEntry);

        });

        return chartData;
    }

    @Override
    public List<ChartEntry> getCalcChartData(Patient p) {
        SortedSet<Feature> features = featureRepository.findByPatient(p);
        this.calculate(features);

        List<ChartEntry> chartData = new ArrayList<>();

        features.forEach(k -> {
            Map<String, Object> entry = new HashMap<String, Object>();
            entry.putAll(k.getCalcResults());

            MapChartEntry chartEntry = new MapChartEntry<String, Object>();
            chartEntry.setData(entry);
            chartEntry.setLabel(k.getVisitDate());

            chartData.add(chartEntry);

        });

        return chartData;
    }

    @Override
    public Feature createOrUpdate(Feature f) throws FeatureException {
        validateFeature(f);
        Feature feature = featureRepository.save(f);
        return feature;
    }

    @Override
    public void deleteFeature(Feature f) {
        f.setPatient(null);
        f.getParameters().clear();
    }


    private void validateFeature(Feature feature) throws FeatureException {
        if (feature.getId() != null && featureRepository.exists(feature.getId())) {
            Feature source = featureRepository.findOne(feature.getId());
            feature.setPatient(source.getPatient());
        }

        if (!feature.getFeatureParameters().isEmpty()) {
            feature.getFeatureParameters().forEach((k, v) -> {
                if (parameterService.existByMnemonic(k)) {
                    Parameter parameter = parameterService.getByMnemonic(k);
                    ParameterValue parameterValue = parameterValueService.resolveParameterValue(parameter, feature, v);
                    feature.getParameters().put(parameter, parameterValue);
                }
            });
        } else {
            throw new FeatureException(FeatureExceptionMessages.INVALID_PARAMETER.create(feature.getPatient().getName().toString(), feature.getVisitDate().toString()));
        }
    }

    @Override
    public boolean exists(Long id) {
        return featureRepository.exists(id);
    }

    private void calculate(Feature feature) {
        calculator.calculate(feature);
    }

    private void calculate(Collection<Feature> features) {
        calculator.calculateAll(features);
    }


}
