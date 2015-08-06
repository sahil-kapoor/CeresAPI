package com.ceres.domain.entity.parameter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by leonardo on 21/03/15.
 */
@Entity
@DiscriminatorValue(value = "double")
@XmlRootElement(namespace = "ceres",name = "parameterDouble")
public class ParameterDouble extends ParameterValue<Double> {

    @Column(name = "DOUBLE_VALUE")
    private Double value;

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public void setValue(Double s) {
        this.value = s;
    }

    public ParameterDouble() {
    }

    public ParameterDouble(Double value) {
        this.value = value;
    }

    public ParameterDouble(Object value) {
        if (value instanceof Integer) {
            Integer i = (int) value;
            this.value = i.doubleValue();
        } else {
            this.value = (Double) value;
        }
    }
}
