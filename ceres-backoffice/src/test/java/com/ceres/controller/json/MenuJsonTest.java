package com.ceres.controller.json;


import com.ceres.domain.embedabble.Contact;
import com.ceres.domain.embedabble.Name;
import com.ceres.domain.embedabble.Portion;
import com.ceres.domain.entity.human.Gender;
import com.ceres.domain.entity.human.Nutritionist;
import com.ceres.domain.entity.human.NutritionistBuilder;
import com.ceres.domain.entity.human.Patient;
import com.ceres.domain.entity.menu.*;
import com.ceres.domain.entity.taco.FoodBuilder;
import com.ceres.domain.entity.taco.MeasureUnit;
import org.junit.Before;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Leonardo on 25/01/2015.
 */
public class MenuJsonTest extends JsonTester {

    Menu menu;
    Nutritionist nutri;
    Patient pat;

    private static final String MENU_JSON = "{jsonID:1,id:null,name:Perde Peso III,creationDate:2014-12-12,validityDate:2014-12-18}";

    @Before
    public void prepare() throws JAXBException {
        initializeJsonMapper();

        nutri = new NutritionistBuilder()
                .setBirthdate(LocalDate.of(2014, 12, 12))
                .setContact(new Contact("abc@email.com"))
                .setCRN("12810923")
                .setGender(Gender.MALE)
                .setName(new Name("Primeiro_Nome", "Sobrenome"))
                .build();


        menu = new MenuBuilder()
                .withName("Perde Peso III")
                .setValidityDate(LocalDate.of(2014, 12, 18))
                .setCreationDate(LocalDate.of(2014, 12, 12))
                .setNutritionist(nutri)
                .addAllMenuContent(createMenuContents())
                .build();

    }


    @Override
    public void shouldSerializeToJson() throws IOException {
        String json = mapper.writeValueAsString(menu).replace("\"", "");
        assertEquals(MENU_JSON, json);
    }

    @Override
    public void shouldDeserializeFromJson() throws IOException {
        String json = mapper.writeValueAsString(menu);
        Menu menuDeserialized = mapper.readValue(json, Menu.class);

        assertNotNull(menuDeserialized);
        assertTrue(menu.equals(menuDeserialized));

    }

    private Set<MenuContent> createMenuContents() {
        Set<MenuContent> menuContents = new HashSet<>();

        menuContents.add(new MenuContentBuilder()
                .setMealType(MealType.BREAKFAST)
                .addFood(new FoodBuilder().setId(1L).setName("Pão").setPortion(new Portion(2.00, MeasureUnit.G)).build(), 2.00)
                .addFood(new FoodBuilder().setId(2L).setName("Cereal").setPortion(new Portion(2.00, MeasureUnit.G)).build(), 1.0).build());

        return menuContents;
    }

}
