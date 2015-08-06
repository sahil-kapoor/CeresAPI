package com.ceres.load;

import com.ceres.domain.entity.human.ActivityRate;
import com.ceres.domain.entity.human.Feature;
import com.ceres.domain.entity.human.FeatureBuilder;
import com.ceres.domain.entity.human.Patient;
import com.ceres.domain.entity.parameter.*;
import com.ceres.domain.repository.human.FeatureRepository;
import com.ceres.domain.repository.human.PatientRepository;
import com.ceres.domain.repository.parameter.ParameterCategoryRepository;
import com.ceres.service.parameter.helper.ParameterValueHelper;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by leonardo on 17/03/15.
 */
@Component
public class FeatureDataLoad implements DataLoad, Ordered {

    @Inject
    private ParameterCategoryRepository paramGroupRep;

    @Inject
    private FeatureRepository featureRep;

    @Inject
    private PatientRepository patientRep;

    @Override
    public void load() {

        for (int i = 0; i <= 1; i++) {
            Patient patient = patientRep.findAll().get(i);
            List<ParameterValue> paramValues = new CopyOnWriteArrayList<>();

            paramGroupRep.findByName("Medidas Padrão").getParameters().stream().forEachOrdered(k -> paramValues.add(createRandomParam(k)));

            paramGroupRep.findByName("Medidas com Adipômetro").getParameters().stream()
                    .forEachOrdered(k -> paramValues.add(createRandomParam(k)));

            paramGroupRep.findByName("Medidas com Fita Métrica").getParameters().stream()
                    .forEachOrdered(k -> paramValues.add(createRandomParam(k)));


            Feature feature = new FeatureBuilder()
                    .setPatient(patient)
                    .setVisitDate(LocalDate.now())
                    .addParameterValues(new ArrayList<>(paramValues))
                    .build();

            feature = featureRep.save(feature);
        }
        List<Feature> features = featureRep.findAll();
    }

    private ParameterValue createRandomParam(Parameter k) {
        ParameterValue valueParam;
        valueParam = ParameterValueHelper.createEmptyParameterValue(k);
        fillWithRandomValues(valueParam);
        return valueParam;
    }

    private void fillWithRandomValues(ParameterValue value) {
        if (value instanceof ParameterInteger)
            fillRandom((ParameterInteger) value);

        if (value instanceof ParameterDouble)
            fillRandom((ParameterDouble) value);

        if (value instanceof ParameterDate)
            fillRandom((ParameterDate) value);

        if (value instanceof ParameterBoolean)
            fillRandom((ParameterBoolean) value);

        if (value instanceof ParameterString)
            fillRandom((ParameterString) value);

        if (value instanceof ParameterActivityRate)
            fillRandom((ParameterActivityRate) value);


    }

    private void fillRandom(ParameterActivityRate param) {
        param.setValue(ActivityRate.values()[(int) (Math.random() * 5)]);
    }

    private void fillRandom(ParameterInteger param) {
        param.setValue((int) (Math.random() * 15));
    }

    private void fillRandom(ParameterDate param) {
        param.setValue(LocalDate.now().minusDays((int) (Math.random() * 75)));
    }

    private void fillRandom(ParameterDouble param) {
        param.setValue(Math.random() * 15);
    }

    private void fillRandom(ParameterBoolean param) {
        param.setValue(Boolean.valueOf(String.valueOf((int) (Math.random() * 1))));
    }

    private void fillRandom(ParameterString param) {
        param.setValue("STRING SIMPLES");
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
