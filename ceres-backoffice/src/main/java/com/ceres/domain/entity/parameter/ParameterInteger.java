package com.ceres.domain.entity.parameter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by leonardo on 21/03/15.
 */
@Entity
@DiscriminatorValue(value = "integer")
@XmlRootElement(namespace = "ceres",name = "parameterInteger")
public class ParameterInteger extends ParameterValue<Integer> {

    @Column(name = "INTEGER_VALUE")
    private Integer value;

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public void setValue(Integer s) {
        this.value = s;
    }

    public ParameterInteger() {
    }

    public ParameterInteger(Integer value) {
        this.value = value;
    }

    public ParameterInteger(Object value) {
        this.value = (Integer) value;
    }
}
