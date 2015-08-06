package com.ceres.load;

import com.ceres.domain.entity.human.Nutritionist;
import com.ceres.domain.entity.human.Patient;
import com.ceres.domain.entity.menu.*;
import com.ceres.domain.repository.human.NutritionistRepository;
import com.ceres.domain.repository.human.PatientRepository;
import com.ceres.domain.repository.menu.MenuRepository;
import com.ceres.domain.repository.menu.MenuTemplateRepository;
import com.ceres.domain.repository.taco.FoodRepository;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by leonardo on 17/03/15.
 */
@Component
public class MenuDataLoad implements DataLoad, Ordered {

    @Inject
    private MenuRepository menuRep;

    @Inject
    private FoodRepository foodRep;

    @Inject
    private PatientRepository patientRep;

    @Inject
    private MenuTemplateRepository templateRep;

    @Inject
    private NutritionistRepository nutriRep;

    @Override
    public void load() {
        List<Patient> patients = patientRep.findAll();
        List<Nutritionist> nutri = nutriRep.findAll();

        createTemplateMenu("Template Teste I", nutri);

        createMenu("Criado Programaticamente I", patients, nutri, false);
        createMenu("Criado Programaticamente II", patients, nutri, false);
        createMenu("Criado Programaticamente III", patients, nutri, true);

        Menu menu = menuRep.findOne(4L);
        templateRep.save(menu.makeTemplate("Herdado do " + menu.getName()));
    }

    private void createTemplateMenu(String name, List<Nutritionist> nutri) {
        MenuTemplate menu = new MenuTemplateBuilder()
                .setCreationDate(LocalDate.now())
                .withName(name)
                .withName(name)
                .setNutritionist(nutri.get((int) (Math.random() * 2)))
                .addAllMenuContent(randomMenuContents())
                .build();

        templateRep.save(menu);
    }

    private void createMenu(String name, List<Patient> patients, List<Nutritionist> nutri, boolean shouldClone) {
        Menu menu = new MenuBuilder()
                .setCreationDate(LocalDate.now())
                .withName(name)
                .setValidityDate(LocalDate.now().plusMonths((int) (Math.random() * 7)))
                .setPatient(patients.get((int) (Math.random() * 1)))
                .setNutritionist(nutri.get((int) (Math.random() * 1)))
                .setCloneAsTemplate(shouldClone)
                .addAllMenuContent(randomMenuContents())
                .build();

        menuRep.save(menu);
    }

    private Set<MenuContent> randomMenuContents() {
        Set<MenuContent> menuContents = new HashSet<>();

        menuContents.add(new MenuContentBuilder()
                .setMealType(MealType.BREAKFAST)
                .addFood(foodRep.findOne((long) (Math.random() * 100) + 1), 1.00)
                .addFood(foodRep.findOne((long) (Math.random() * 100) + 1), 1.00)
                .build());

        menuContents.add(new MenuContentBuilder()
                .setMealType(MealType.LUNCH)
                .addFood(foodRep.findOne((long) (Math.random() * 200) + 1), 1.5)
                .addFood(foodRep.findOne((long) (Math.random() * 200) + 1), 1.5)
                .build());
        menuContents.add(new MenuContentBuilder()
                .setMealType(MealType.DINNER)
                .addFood(foodRep.findOne((long) (Math.random() * 300) + 1), 1.00)
                .addFood(foodRep.findOne((long) (Math.random() * 300) + 1), 1.00)
                .build());

        return menuContents;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
