package com.ceres.controller.json;


import com.ceres.domain.entity.taco.Food;
import com.ceres.domain.entity.taco.FoodBuilder;
import com.ceres.domain.entity.taco.FoodCategory;
import org.junit.Before;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Leonardo on 25/01/2015.
 */
public class FoodCategoryJsonTest extends JsonTester {

    FoodCategory foodCategory;

    private static final String FOOD_CATEGORY_JSON = "{jsonID:1,id:2,name:Grão,foods:[{jsonID:2,id:3,name:Arroz,category:null,portion:{value:100.0,measureUnit:G},nutriFacts:{},homemadeMeasureValues:[]}]}";

    @Before
    public void prepare() throws JAXBException {
        initializeJsonMapper();
        Set<Food> someFoods = createFoods();
        foodCategory = new FoodCategory("Grão", someFoods);
        foodCategory.setId((long) 2);
    }

    private Set<Food> createFoods() {
        Set<Food> foods = new HashSet<>();
        foods.add(new FoodBuilder()
                .setId(3L)
                .setName("Arroz")
                .setFoodCategory(foodCategory)
                .withDefaultPortion()
                .build());
        return foods;
    }

    @Override
    public void shouldSerializeToJson() throws IOException {
        String json = mapper.writeValueAsString(foodCategory).replace("\"", "");

        assertEquals(FOOD_CATEGORY_JSON, json);
    }

    @Override
    public void shouldDeserializeFromJson() throws IOException {
        String json = mapper.writeValueAsString(foodCategory);
        FoodCategory foodCategoryDeserialized = mapper.readValue(json, FoodCategory.class);

        assertNotNull(foodCategoryDeserialized);
        assertTrue(foodCategory.equals(foodCategoryDeserialized));

    }

}
