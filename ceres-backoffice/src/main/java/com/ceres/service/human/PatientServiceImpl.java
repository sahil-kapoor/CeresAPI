package com.ceres.service.human;

import com.ceres.business.io.IOService;
import com.ceres.domain.entity.human.Feature;
import com.ceres.domain.entity.human.Patient;
import com.ceres.domain.repository.human.HumanRepository;
import com.ceres.service.human.exception.FeatureException;
import com.ceres.service.human.exception.HumanException;
import com.ceres.service.human.exception.HumanExceptionMessages;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leonardo on 22/03/15.
 */
@Service(value = "PatientService")
public final class PatientServiceImpl implements PatientService {

    private HumanRepository humanRepository;

    private FeatureService featureService;

    private IOService ioService;

    @Inject
    public PatientServiceImpl(HumanRepository humanRepository, FeatureService featureService, IOService ioService) {
        this.humanRepository = humanRepository;
        this.featureService = featureService;
        this.ioService = ioService;
    }

    @Override
    public Patient getById(Long id) {
        return (Patient) humanRepository.findOne(id);
    }

    @Override
    public Patient getByIdWithCalculatedFeatures(Long id) {
        Patient p = (Patient) humanRepository.findOne(id);
        featureService.calculateFeatures(p);
        return p;
    }


    @Override
    public List<Patient> getAll() {
        List<Patient> patients = new ArrayList<>();
        humanRepository.findAll().stream().forEach(k -> {
            if (k instanceof Patient)
                patients.add((Patient) k);
        });
        return patients;
    }

    @Override
    public void remove(Long id) {
        humanRepository.delete(id);
    }

    @Override
    public Patient update(Patient patient) throws HumanException {
        validatePatient(patient);

        return humanRepository.save(patient);
    }

    @Override
    public Patient create(Patient patient) throws HumanException {
        validatePatient(patient);

        return humanRepository.save(patient);
    }

    @Override
    public File exportToXmlFile(Long id) {
        Patient p = (Patient) humanRepository.findOne(id);
        String fileName = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " - " + p.getName().toString();
        return ioService.exportTarget(fileName, p);
    }

    @Override
    public Patient importFromXml(File xmlFile) {
        Patient importedPatient = (Patient) ioService.importTarget(xmlFile, Patient.class);
        importedPatient.setId(null);
        return humanRepository.save(importedPatient);
    }

    @Override
    public boolean exists(Long id) {
        return humanRepository.exists(id);
    }

    @Override
    public Feature registryFeature(Patient p, Feature f) throws HumanException, FeatureException {
        f.setPatient(p);
        Feature newFeature = featureService.createOrUpdate(f);
        p.addFeature(newFeature);
        this.update(p);
        return newFeature;
    }

    @Override
    public void removeFeatureFromPatient(Patient p, Feature feature) {
        p.removeFeature(feature);
        featureService.deleteFeature(feature);
        humanRepository.save(p);
    }

    private void validatePatient(Patient patient) throws HumanException {
        if (patient.getName() == null || patient.getName().isNullOrEmpty()) {
            throw new HumanException(HumanExceptionMessages.INCOMPLETE.create("Nome"));
        } else if (patient.getGender() == null) {
            throw new HumanException(HumanExceptionMessages.INCOMPLETE.create("Sexo"));
        } else if (patient.getBirthdate() == null) {
            throw new HumanException(HumanExceptionMessages.INCOMPLETE.create("Nascimento"));
        } else if (patient.getContact().isNullOrEmpty()) {
            throw new HumanException(HumanExceptionMessages.INCOMPLETE.create("Contato"));
        }
    }
}
