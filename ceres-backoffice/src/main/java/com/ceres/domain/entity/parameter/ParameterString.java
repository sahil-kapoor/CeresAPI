package com.ceres.domain.entity.parameter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by leonardo on 21/03/15.
 */
@Entity
@DiscriminatorValue(value = "text")
@XmlRootElement(namespace = "ceres",name = "parameterText")
public class ParameterString extends ParameterValue<String> {

    @Column(name = "TEXT_VALUE")
    private String value;

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String s) {
        this.value = s;
    }

    public ParameterString() {
    }

    public ParameterString(String string) {
        this.value = string;
    }

    public ParameterString(Object string) {
        this.value = (String) string;
    }

}
