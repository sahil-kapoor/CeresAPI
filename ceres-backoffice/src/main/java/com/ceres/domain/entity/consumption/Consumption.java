package com.ceres.domain.entity.consumption;

import com.ceres.domain.entity.AbstractEntity;
import com.ceres.domain.entity.human.Patient;
import com.ceres.domain.entity.menu.MealType;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by leonardo on 25/04/15.
 */
@Entity
@Table(name = "CONSUMPTION")
@ApiModel(value = "consumptions")
@XmlRootElement(namespace = "ceres")
public class Consumption extends AbstractEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_HUMAN_CONSUMPTION_ID"), nullable = false)
    private Patient patient;

    @Column(nullable = false, name = "PERIOD")
    @Enumerated(value = EnumType.STRING)
    private MealType period;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "HUMAN_CONSUMPTION")
    @Column(name = "PORTION_QUANTITY", nullable = false)
    @MapKeyColumn(name = "FOOD_ID")
    @ApiModelProperty(value = "foods", dataType = "String", example = "foodId:portion", required = true)
    private Map<Long, Double> consumptions = new HashMap<>();

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public MealType getPeriod() {
        return period;
    }

    public void setPeriod(MealType period) {
        this.period = period;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consumption that = (Consumption) o;
        return Objects.equals(getPatient(), that.getPatient()) &&
                Objects.equals(getPeriod(), that.getPeriod());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPatient(), getPeriod());
    }
}
