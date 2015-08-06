package com.ceres.domain.entity.taco;

import com.ceres.domain.entity.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Leonardo on 25/02/2015.
 */
@Entity
@Table(name = "ALLERGEN_CATEGORY")
@XmlRootElement(namespace = "ceres")
@ApiModel(value = "allergenCategory")
public class AllergenCategory extends AbstractEntity implements Comparable<AllergenCategory> {

    @NaturalId
    @Column(nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @org.hibernate.annotations.ForeignKey(name = "FK_ALLERGEN_ID", inverseName = "FK_ALLERGEN_FOOD_ID")
    @JoinTable(
            name = "ALLERGEN",
            joinColumns = {@JoinColumn(name = "ALLERGEN_CATEGORY_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "FOOD_ID", referencedColumnName = "ID")}
    )
    @XmlElementWrapper(name = "ALLERGENS")
    @JsonManagedReference
    @ApiModelProperty(value = "food")
    private Set<Food> foods = new HashSet<>();

    protected AllergenCategory() {

    }

    public AllergenCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Food> getFoods() {
        return foods;
    }

    public void addFoods(Set<Food> foods) {
        this.foods.addAll(foods);
    }

    public void addFood(Food food) {
        this.foods.add(food);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AllergenCategory)) return false;

        AllergenCategory that = (AllergenCategory) o;

        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "AllergenCategory{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(AllergenCategory o) {
        return this.getName().compareTo(o.getName());
    }

}
