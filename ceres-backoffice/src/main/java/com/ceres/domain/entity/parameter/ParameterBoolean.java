package com.ceres.domain.entity.parameter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by leonardo on 21/03/15.
 */
@Entity
@DiscriminatorValue(value = "boolean")
@XmlRootElement(namespace = "ceres",name = "parameterBoolean")
public class ParameterBoolean extends ParameterValue<Boolean> {

    @Column(name = "BOOLEAN_VALUE")
    private Boolean value;

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public void setValue(Boolean s) {
        this.value = s;
    }

    public ParameterBoolean() {
    }

    public ParameterBoolean(Boolean value) {
        this.value = value;
    }

    public ParameterBoolean(Object value) {
        this.value = (Boolean) value;
    }

}
