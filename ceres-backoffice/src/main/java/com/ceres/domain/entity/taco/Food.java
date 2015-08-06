package com.ceres.domain.entity.taco;

import com.ceres.domain.embedabble.Portion;
import com.ceres.domain.entity.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

/**
 * Created by Leonardo on 25/02/2015.
 */
@Entity
@Table(name = "FOOD",
        indexes = {@Index(name = "FOOD_NAME_INDEX", columnList = "NAME", unique = true)})
@XmlRootElement(namespace = "ceres")
@ApiModel(value = "food")
public class Food extends AbstractEntity implements Comparable<Food> {

    @NaturalId
    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORY_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_CATEGORY_ID"))
    @JsonIgnoreProperties(value = {"foods"})
    @ApiModelProperty(value = "category", dataType = "Long")
    private FoodCategory category;

    @Embedded
    private Portion portion;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "FOOD_NUTRIFACT", joinColumns = @JoinColumn(name = "FOOD_ID"))
    @Column(name = "VALUE", nullable = false)
    @MapKeyJoinColumn(name = "NAME", referencedColumnName = "NAME", nullable = false, table = "NUTRIFACT")
    private final Map<String, Double> nutriFacts;

    @Column(nullable = true)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "food", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private final Set<Measure> homemadeMeasureValues;

    public Food() {
        homemadeMeasureValues = new HashSet<>();
        nutriFacts = new HashMap<>();
    }

    public Food(String name, FoodCategory category, Portion portion) {
        this.name = name;
        this.category = category;
        this.portion = portion;

        homemadeMeasureValues = new HashSet<>();
        nutriFacts = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public FoodCategory getCategory() {
        return category;
    }

    public Map<String, Double> getNutriFacts() {
        return Collections.unmodifiableMap(nutriFacts);
    }

    public void addNutriFact(NutriFact nutriFact, Double value) {
        this.nutriFacts.put(nutriFact.getName(), value);
    }

    public Set<Measure> getHomemadeMeasureValues() {
        return Collections.unmodifiableSet(homemadeMeasureValues);
    }

    public void addMeasure(Measure measure) {
        measure.setFood(this);
        this.homemadeMeasureValues.add(measure);
    }

    public Portion getPortion() {
        return portion;
    }

    @Override
    public int compareTo(Food o) {
        return this.name.compareTo(o.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return Objects.equals(getName(), food.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return name;
    }

}
