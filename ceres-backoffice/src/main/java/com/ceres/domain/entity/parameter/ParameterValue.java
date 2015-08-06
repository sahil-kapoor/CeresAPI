package com.ceres.domain.entity.parameter;

import com.ceres.domain.entity.AbstractEntity;
import com.ceres.domain.entity.human.Feature;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * Created by leonardo on 21/03/15.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", discriminatorType = DiscriminatorType.STRING)
@XmlRootElement(namespace = "ceres")
public abstract class ParameterValue<T> extends AbstractEntity {

    @ManyToOne
    private Parameter parameter;

    @ManyToOne
    @JoinColumn(name = "FEATURE_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_PARAMETER_FEATURE_ID"), unique = false)
    @JsonIgnore
    private Feature feature;

    public abstract T getValue();

    public abstract void setValue(T t);

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    @Override
    public String toString() {
        return parameter.getMnemonic();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParameterValue<?> that = (ParameterValue<?>) o;
        return Objects.equals(getParameter(), that.getParameter()) &&
                Objects.equals(getFeature(), that.getFeature());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getParameter(), getFeature());
    }
}
