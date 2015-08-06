package com.ceres.service.human;

import com.ceres.business.io.IOService;
import com.ceres.domain.entity.human.Nutritionist;
import com.ceres.domain.repository.human.HumanRepository;
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
@Service
public final class NutritionistServiceImpl implements NutritionistService {

    private HumanRepository nutritionistRepository;

    private IOService ioService;

    @Inject
    public NutritionistServiceImpl(HumanRepository nutritionistRepository, IOService ioService) {
        this.nutritionistRepository = nutritionistRepository;
        this.ioService = ioService;
    }

    @Override
    public Nutritionist getById(Long id) {
        return (Nutritionist) nutritionistRepository.findOne(id);
    }

    @Override
    public List<Nutritionist> getAll() {
        List<Nutritionist> nutritionists = new ArrayList<>();
        nutritionistRepository.findAll().stream().forEach(k -> {
            if (k instanceof Nutritionist)
                nutritionists.add((Nutritionist) k);
        });
        return nutritionists;
    }

    @Override
    public void remove(Long id) {
        nutritionistRepository.delete(id);
    }

    @Override
    public Nutritionist update(Nutritionist nutritionist) throws HumanException {
        validateNutritionist(nutritionist);
        return nutritionistRepository.save(nutritionist);
    }

    @Override
    public Nutritionist create(Nutritionist nutritionist) throws HumanException {
        validateNutritionist(nutritionist);
        return nutritionistRepository.save(nutritionist);
    }

    @Override
    public File exportToXmlFile(Long id) {
        Nutritionist n = (Nutritionist) nutritionistRepository.findOne(id);
        String fileName = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " - " + n.getName().toString();
        return ioService.exportTarget(fileName, n);
    }

    @Override
    public Nutritionist importFromXml(File xmlFile) {
        Nutritionist importedPatient = (Nutritionist) ioService.importTarget(xmlFile, Nutritionist.class);
        importedPatient.setId(null);
        return nutritionistRepository.save(importedPatient);
    }

    @Override
    public boolean exists(Long id) {
        return nutritionistRepository.exists(id);
    }


    private void validateNutritionist(Nutritionist nutri) throws HumanException {
        if (nutri.getName() == null && nutri.getName().isNullOrEmpty()) {
            throw new HumanException(HumanExceptionMessages.INCOMPLETE.create("Nome"));
        } else if (nutri.getGender() == null) {
            throw new HumanException(HumanExceptionMessages.INCOMPLETE.create("Sexo"));
        } else if (nutri.getContact().isNullOrEmpty()) {
            throw new HumanException(HumanExceptionMessages.INCOMPLETE.create("Contato"));
        } else if (nutri.getCrn() == null || nutri.getCrn().isEmpty()) {
            throw new HumanException(HumanExceptionMessages.INCOMPLETE.create("CRN"));
        }
    }
}
