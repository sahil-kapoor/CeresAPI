package com.ceres.domain.entity.taco;

import com.ceres.domain.entity.AbstractEntity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Leonardo on 25/02/2015.
 */
@Entity
@Table(name = "NUTRIFACT",
        indexes = {@Index(name = "NUTRIFACT_NAME_INDEX", columnList = "NAME", unique = false)})
@XmlRootElement(namespace = "ceres")
public class NutriFact extends AbstractEntity implements Serializable, Comparable<NutriFact> {

    @Column(nullable = false, name = "NAME")
    private String name;

    @Column(nullable = false, name = "MEASURE_UNIT")
    @Enumerated(value = EnumType.STRING)
    private MeasureUnit measureUnit;

    public NutriFact() {
    }

    public NutriFact(NutriFactType nutrifact, MeasureUnit measureUnit) {
        this.name = nutrifact.value();
        this.measureUnit = measureUnit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MeasureUnit getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(MeasureUnit measureUnit) {
        this.measureUnit = measureUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NutriFact nutriFact = (NutriFact) o;
        return Objects.equals(getName(), nutriFact.getName()) &&
                Objects.equals(getMeasureUnit(), nutriFact.getMeasureUnit());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getMeasureUnit());
    }

    @Override
    public int compareTo(NutriFact o) {
        return this.name.compareTo(o.getName());
    }

    @Override
    public String toString() {
        return name + " " + measureUnit.name();
    }

}
