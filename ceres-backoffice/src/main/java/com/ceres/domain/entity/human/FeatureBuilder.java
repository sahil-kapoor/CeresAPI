package com.ceres.domain.entity.human;

import com.ceres.domain.Builder;
import com.ceres.domain.entity.parameter.ParameterValue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FeatureBuilder implements Builder<Feature> {

    private LocalDate visitDate;
    private Patient patient;
    private List<ParameterValue> parameters = new ArrayList<>();


    public FeatureBuilder setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
        return this;
    }

    public FeatureBuilder setPatient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public FeatureBuilder addParameterValue(ParameterValue featureValue) {
        this.parameters.add(featureValue);
        return this;
    }

    public FeatureBuilder addParameterValues(List<ParameterValue> featureValues) {
        this.parameters.addAll(featureValues);
        return this;
    }

    @Override
    public Feature build() {
        Feature feature = new Feature();
        feature.setPatient(patient);
        feature.setVisitDate(visitDate);

        parameters.forEach(feature::addParameter);

        return feature;
    }
}