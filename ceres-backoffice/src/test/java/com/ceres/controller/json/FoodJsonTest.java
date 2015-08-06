package com.ceres.controller.json;


import com.ceres.domain.entity.taco.*;
import org.junit.Before;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by Leonardo on 25/01/2015.
 */
public class FoodJsonTest extends JsonTester {

    private static final String FOOD_JSON = "{jsonID:1,id:3,name:Arroz,category:{jsonID:2,id:2,name:Grão},portion:{value:100.0,measureUnit:G},nutriFacts:{Carboidrato:2.22},homemadeMeasureValues:[{jsonID:3,id:1,homemadeMeasure:{jsonID:4,id:6,name:Fatia},portionQuantity:100.0}]}";
    FoodCategory foodCategory;
    NutriFact nutriFact;
    Food food;

    @Before
    public void prepare() throws JAXBException {
        initializeJsonMapper();

        foodCategory = new FoodCategory("Grão");
        foodCategory.setId((long) 2);
        nutriFact = new NutriFact(NutriFactType.CARBOHYDRATE, MeasureUnit.PCT);
        nutriFact.setId((long) 21);

        food = new FoodBuilder()
                .setId(3L)
                .setName("Arroz")
                .setFoodCategory(foodCategory)
                .withDefaultPortion()
                .build();
        food.addNutriFact(nutriFact, 2.22);
        HomemadeMeasure fatia = new HomemadeMeasure("Fatia");
        fatia.setId(6L);
        food.addMeasure(new MeasureBuilder()
                .setId(1L)
                .setMeasure(fatia)
                .setPortionQuantity(100.00)
                .build());
    }

    @Override
    public void shouldSerializeToJson() throws IOException {
        String json = mapper.writeValueAsString(food).replace("\"", "");
        assertEquals(FOOD_JSON, json);
    }

    @Override
    public void shouldDeserializeFromJson() throws IOException {
        String json = mapper.writeValueAsString(food);
        Food foodDeserialized = mapper.readValue(json, Food.class);

        assertNotNull(foodDeserialized);
        assertTrue(food.equals(foodDeserialized));


    }

}
