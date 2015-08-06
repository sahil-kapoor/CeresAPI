package com.ceres.domain.entity.taco;

import com.ceres.domain.entity.AbstractEntity;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Leonardo on 25/02/2015.
 */
@Entity
@Table(name = "FOOD_CATEGORY",
        indexes = {@Index(name = "CATEGORY_NAME_INDEX", columnList = "NAME", unique = true)})
@XmlRootElement(namespace = "ceres")
public class FoodCategory extends AbstractEntity implements Comparable<FoodCategory> {

    @Column(nullable = false, name = "NAME")
    private String name;

    @Column(nullable = false, name = "FOOD_ID")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category", cascade = CascadeType.REMOVE)
    @ApiModelProperty(value = "food")
    private Set<Food> foods = new HashSet<>();

    public FoodCategory() {
    }

    public FoodCategory(String name) {
        this.name = name;
    }

    public FoodCategory(String name, Set<Food> foods) {
        this.name = name;
        this.foods = foods;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Food> getFoods() {
        return Collections.unmodifiableSet(foods);
    }

    @Override
    public int compareTo(FoodCategory o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FoodCategory)) return false;

        FoodCategory that = (FoodCategory) o;

        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

}
