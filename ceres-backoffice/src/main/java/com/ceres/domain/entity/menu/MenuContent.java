package com.ceres.domain.entity.menu;

import com.ceres.domain.entity.AbstractEntity;
import com.ceres.domain.entity.taco.Food;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by leonardo on 14/03/15.
 */
@Entity
@Table(name = "MENU_CONTENT")
@ApiModel(value = "menuContent")
@XmlRootElement(namespace = "ceres")
public class MenuContent extends AbstractEntity implements Cloneable, Comparable<MenuContent> {

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_MENU_ID"))
    @JsonBackReference
    private AbstractMenu menu;

    @Column(nullable = true, name = "MEAL_TYPE", unique = false)
    @Enumerated(value = EnumType.STRING)
    private MealType mealType;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "MENU_CONTENT_FOOD")
    @Column(name = "PORTION_QUANTITY", nullable = false)
    @MapKeyColumn(name = "FOOD_ID")
    @ApiModelProperty(value = "foods", dataType = "String", example = "foodId:portion", required = true)
    private Map<Long, Double> foods = new HashMap<>();

    MenuContent() {
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public AbstractMenu getMenu() {
        return menu;
    }

    public void setMenu(AbstractMenu menu) {
        this.menu = menu;
    }

    public Map<Long, Double> getFoods() {
        return Collections.unmodifiableMap(foods);
    }

    public void addFood(Food food, Double portion) {
        this.foods.put(food.getId(), portion);
    }

    @Override
    public int compareTo(MenuContent o) {
        return this.mealType.compareTo(o.getMealType());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuContent that = (MenuContent) o;
        return Objects.equals(getMenu(), that.getMenu()) &&
                Objects.equals(getMealType(), that.getMealType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMenu(), getMealType());
    }

    @Override
    public String toString() {
        return mealType.name();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        MenuContent menuContent = (MenuContent) super.clone();
        menuContent.setId(null);
        return menuContent;
    }

}
