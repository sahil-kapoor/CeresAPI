package com.ceres.controller.health;

import com.ceres.controller.v1.menu.MenuContentController;
import com.ceres.domain.embedabble.Contact;
import com.ceres.domain.embedabble.Name;
import com.ceres.domain.embedabble.Portion;
import com.ceres.domain.entity.human.*;
import com.ceres.domain.entity.menu.*;
import com.ceres.domain.entity.taco.Food;
import com.ceres.domain.entity.taco.FoodBuilder;
import com.ceres.domain.entity.taco.FoodCategory;
import com.ceres.domain.entity.taco.MeasureUnit;
import com.ceres.domain.repository.human.NutritionistRepository;
import com.ceres.domain.repository.human.PatientRepository;
import com.ceres.domain.repository.menu.MenuRepository;
import com.ceres.domain.repository.taco.CategoryRepository;
import com.ceres.domain.repository.taco.FoodRepository;
import com.ceres.suites.integrated.AbstractDbEnvTestSuite;
import org.glassfish.jersey.server.internal.routing.UriRoutingContext;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by leonardo on 18/04/15.
 */
public class MenuContentHealth extends AbstractDbEnvTestSuite {

    @Inject
    MenuContentController menuContentController;

    @Inject
    NutritionistRepository nutritionistRepository;

    @Inject
    PatientRepository patientRepository;

    @Inject
    MenuRepository menuRepository;

    @Inject
    FoodRepository foodRepository;

    @Inject
    CategoryRepository categoryRepository;

    Menu m;

    Set<MenuContent> meals = new HashSet<>();

    Nutritionist nutritionist;

    Patient patient;

    @Before
    public void before() {

        menuContentController.setUriInfo(new UriRoutingContext(getFakeContainerRequest()));

        patient = new PatientBuilder()
                .setName(new Name("Sr.", "Leonardo", "Rogerio"))
                .setCpf("123123123")
                .setBirthdate(LocalDate.of(2014, 12, 12))
                .setGender(Gender.MALE)
                .setContact(new Contact("pati@email.com"))
                .setPassword("leo")
                .build();

        patient = patientRepository.save(patient);

        nutritionist = new NutritionistBuilder()
                .setName(new Name("Sr.", "Leonardo", "Rogerio"))
                .setBirthdate(LocalDate.of(2014, 12, 12))
                .setGender(Gender.MALE)
                .setContact(new Contact("nutri@email.com"))
                .setPassword("leo")
                .setCRN("C192Ca!")
                .build();

        nutritionist = nutritionistRepository.save(nutritionist);

        m = new MenuBuilder()
                .withName("Perde Peso III")
                .setValidityDate(LocalDate.of(2014, 12, 18))
                .setCreationDate(LocalDate.of(2014, 12, 12))
                .setNutritionist(nutritionist)
                .build();

        menuRepository.save(m);

        FoodCategory cat = categoryRepository.save(new FoodCategory("Grao"));

        Food food1 = new FoodBuilder()
                .setFoodCategory(cat)
                .setName("Pão")
                .setPortion(new Portion(2.00, MeasureUnit.G))
                .build();

        food1 = foodRepository.save(food1);

        Food food2 = new FoodBuilder()
                .setFoodCategory(cat)
                .setName("Cereal")
                .setPortion(new Portion(2.00, MeasureUnit.G))
                .build();

        food2 = foodRepository.save(food2);

        MenuContent content1 = new MenuContentBuilder()
                .setMealType(MealType.BREAKFAST)
                .addFood(food1, 2.00)
                .addFood(food2, 1.5)
                .setMenu(m)
                .build();

        MenuContent content2 = new MenuContentBuilder()
                .setMealType(MealType.LUNCH)
                .addFood(food1, 1.00)
                .addFood(food2, 3.5)
                .setMenu(m)
                .build();

        meals.add(content1);
        meals.add(content2);

        m.addAllMeal(meals);
    }

    @Test
    public void menuContentControllerHealth() {
        Response menuContentResponse;

        menuContentResponse = menuContentController.addFoods(m.getId(), m.getMeals());
        assertEquals(Response.Status.ACCEPTED, menuContentResponse.getStatusInfo());

        menuContentResponse = menuContentController.getMenuContent(m.getMeals().iterator().next().getId());
        assertEquals(Response.Status.OK, menuContentResponse.getStatusInfo());

        menuContentResponse = menuContentController.getAllFromMenu(m.getId());
        assertEquals(Response.Status.OK, menuContentResponse.getStatusInfo());

        menuContentResponse = menuContentController.updateMenuContent(m.getMeals().iterator().next().getId(), m.getMeals().iterator().next());
        assertEquals(Response.Status.OK, menuContentResponse.getStatusInfo());

        menuContentResponse = menuContentController.removeContent(m.getMeals().iterator().next().getId());
        assertEquals(Response.Status.NO_CONTENT, menuContentResponse.getStatusInfo());

    }

}
