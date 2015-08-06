package com.ceres.business;

import com.ceres.domain.embedabble.Name;
import com.ceres.domain.entity.human.*;
import com.ceres.domain.entity.menu.*;
import com.ceres.domain.entity.taco.*;
import com.ceres.domain.repository.human.HumanRepository;
import com.ceres.domain.repository.menu.MenuRepository;
import com.ceres.domain.repository.taco.CategoryRepository;
import com.ceres.domain.repository.taco.FoodRepository;
import com.ceres.domain.repository.taco.NutriFactRepository;
import com.ceres.suites.integrated.AbstractDbEnvTestSuite;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertNotNull;

/**
 * Created by leonardo on 07/03/15.
 */
public class MenuCreationTest extends AbstractDbEnvTestSuite {


    Menu menu;
    @Inject
    private MenuRepository menuRep;
    @Inject
    private CategoryRepository categoryRep;
    @Inject
    private NutriFactRepository nutriRep;
    @Inject
    private FoodRepository foodRep;
    @Inject
    private HumanRepository humanRep;

    @Before
    public void prepare() {

        Patient patient = new PatientBuilder()
                .setName(new Name("Sr.", "Leonardo", "Rogerio"))
                .setCpf("123123123")
                .setBirthdate(LocalDate.now().minusYears(20))
                .setGender(Gender.MALE)
                .build();

        Nutritionist nutritionist = new NutritionistBuilder()
                .setName(new Name("Sr.", "Rogerio", "Leonardo"))
                .setGender(Gender.MALE)
                .setBirthdate(LocalDate.now().minusYears(22))
                .setCRN(String.valueOf((int) (Math.random() * 1000)))
                .build();

        patient = humanRep.save(patient);
        nutritionist = humanRep.save(nutritionist);

        createFood("Arroz", "Cat 1");
        createFood("Feijao", "Cat 2");
        createFood("Leite", "Cat 3");
        createFood("Agua", "Cat 4");
        createFood("Cerveja", "Cat 5");
        createFood("Nacho", "Cat 6");

        Set<MenuContent> menuContents = new HashSet<>();

        menuContents.add(new MenuContentBuilder()
                .setMealType(MealType.BREAKFAST)
                .addFood(foodRep.findByName("Arroz"), 2.5)
                .addFood(foodRep.findByName("Nacho"), 1.0).build());

        menuContents.add(new MenuContentBuilder()
                .setMealType(MealType.BEFORE_DINNER)
                .addFood(foodRep.findByName("Arroz"), 2.00)
                .addFood(foodRep.findByName("Cerveja"), 1.5).build());

        menuContents.add(new MenuContentBuilder()
                .setMealType(MealType.BREAKFAST)
                .addFood(foodRep.findByName("Leite"), 0.5)
                .addFood(foodRep.findByName("Feijao"), 1.0).build());

        menu = new MenuBuilder().
                setCreationDate(LocalDate.now())
                .withName("Cardápio de Teste")
                .setValidityDate(LocalDate.now().plusMonths(3))
                .setPatient(patient)
                .setNutritionist(nutritionist)
                .addAllMenuContent(menuContents)
                .build();

        menu = menuRep.save(menu);


    }

    public void createFood(String name, String category) {

        FoodCategory cat = categoryRep.save(new FoodCategory(category));
        NutriFact nutri = nutriRep.save(new NutriFact(NutriFactType.ENERGY, MeasureUnit.KCAL));

        Food food = new FoodBuilder()
                .setName(name)
                .setFoodCategory(cat)
                .withDefaultPortion()
                .build();

        food.addNutriFact(nutri, 129.00);

        food.addMeasure(new MeasureBuilder()
                .setMeasure(new HomemadeMeasure("Fatia"))
                .setPortionQuantity(2.22)
                .build());
        food.addMeasure(new MeasureBuilder()
                .setMeasure(new HomemadeMeasure("Xícara"))
                .setPortionQuantity(3.00)
                .build());

        foodRep.save(food);

    }

    @Test
    public void deveCriarMenuAssociadoAoPaciente() {
        Menu menu = menuRep.findOne(1L);
        assertNotNull(menu);
    }

}
