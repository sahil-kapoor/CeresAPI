package com.ceres.domain.entity.parameter;

import com.ceres.domain.entity.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wordnik.swagger.annotations.ApiModel;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Created by leonardo on 21/03/15.
 */
@Entity
@Table(name = "PARAMETER_CATEGORY")
@ApiModel(value = "parameterCategory")
@XmlRootElement(namespace = "ceres")
public class ParameterCategory extends AbstractEntity implements Serializable {

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Parameter> parameters;

    ParameterCategory() {
    }

    public ParameterCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Parameter> getParameters() {
        return Collections.unmodifiableList(parameters);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParameterCategory)) return false;

        ParameterCategory that = (ParameterCategory) o;

        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
