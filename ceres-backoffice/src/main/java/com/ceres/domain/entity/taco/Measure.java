package com.ceres.domain.entity.taco;

import com.ceres.domain.entity.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wordnik.swagger.annotations.ApiModel;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Leonardo on 25/02/2015.
 */
@Entity
@Table(name = "MEASURE",
        indexes = {@Index(name = "FOOD_INDEX", columnList = "FOOD_ID", unique = false)})
@ApiModel
@XmlRootElement(namespace = "ceres")
public class Measure extends AbstractEntity implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Food.class)
    @JoinColumn(name = "FOOD_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_FOOD_ID"))
    private Food food;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "HOMEMADE_MEASURE_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_HOMEMADE_MEASURE_ID"))
    @JsonIgnoreProperties(value = "measures")
    private HomemadeMeasure homemadeMeasure;

    @Column(nullable = true, columnDefinition = "NUMERIC", name = "VALUE_IN_PORTION", scale = 2, precision = 2)
    private Double portionQuantity;

    Measure() {
    }

    Measure(Long id, Food food, HomemadeMeasure measurement, Double portionQuantity) {
        this.setId(id);
        this.food = food;
        this.homemadeMeasure = measurement;
        this.portionQuantity = portionQuantity;
    }

    public Double getPortionQuantity() {
        return portionQuantity;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public HomemadeMeasure getHomemadeMeasure() {
        return homemadeMeasure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Measure measure = (Measure) o;
        return Objects.equals(food, measure.food) &&
                Objects.equals(getHomemadeMeasure(), measure.getHomemadeMeasure()) &&
                Objects.equals(getPortionQuantity(), measure.getPortionQuantity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(food, getHomemadeMeasure(), getPortionQuantity());
    }

    @Override
    public String toString() {
        return homemadeMeasure.toString() + " " + portionQuantity.toString();
    }

}
