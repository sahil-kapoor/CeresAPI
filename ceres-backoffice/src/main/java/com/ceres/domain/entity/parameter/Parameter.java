package com.ceres.domain.entity.parameter;

import com.ceres.domain.entity.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wordnik.swagger.annotations.ApiModel;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * Created by Leonardo on 18/03/15.
 */
@Entity
@Table(name = "PARAMETER")
@ApiModel(value = "parameter")
@XmlRootElement(namespace = "ceres")
public class Parameter extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "MNEMONIC")
    private String mnemonic;

    @Column(nullable = false, name = "TYPE")
    @Enumerated(value = EnumType.STRING)
    private ParameterType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"parameters", "name"})
    private ParameterCategory category;

    public static Parameter withName(String name) {
        return new Parameter(name);
    }

    Parameter() {
    }

    private Parameter(String name) {
        this.name = name;
    }

    public Parameter(String mnemonic, ParameterType type) {
        this.mnemonic = mnemonic;
        this.type = type;
    }

    public Parameter(String mnemonic, String name, ParameterType type) {
        this.mnemonic = mnemonic;
        this.name = name;
        this.type = type;
    }


    public Parameter(String mnemonic, ParameterType type, ParameterCategory category) {
        this.mnemonic = mnemonic;
        this.type = type;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ParameterCategory getCategory() {
        return category;
    }

    public void setCategory(ParameterCategory category) {
        this.category = category;
    }

    public ParameterType getType() {
        return type;
    }

    public void setType(ParameterType type) {
        this.type = type;
    }

    public String getMnemonic() {
        return mnemonic.toUpperCase();
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    @Override
    public String toString() {
        return name + " " + type.name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parameter parameter = (Parameter) o;
        return Objects.equals(getMnemonic(), parameter.getMnemonic());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMnemonic());
    }
}
