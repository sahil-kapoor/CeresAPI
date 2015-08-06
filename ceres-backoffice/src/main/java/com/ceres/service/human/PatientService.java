package com.ceres.service.human;

import com.ceres.domain.entity.human.Feature;
import com.ceres.domain.entity.human.Patient;
import com.ceres.service.human.exception.FeatureException;
import com.ceres.service.human.exception.HumanException;

/**
 * Created by leonardo on 22/03/15.
 */
public interface PatientService extends HumanService<Patient> {

    Patient getById(Long id);

    Patient getByIdWithCalculatedFeatures(Long id);

    Feature registryFeature(Patient p, Feature f) throws HumanException, FeatureException;

    void removeFeatureFromPatient(Patient p, Feature feature);
}
