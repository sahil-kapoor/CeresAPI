package com.ceres.domain.entity.taco;

import com.ceres.domain.entity.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wordnik.swagger.annotations.ApiModel;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by leonardo on 09/03/15.
 */
@Entity
@Table(name = "HOMEMADE_MEASURE")
@ApiModel
@XmlRootElement(namespace = "ceres")
public class HomemadeMeasure extends AbstractEntity implements Serializable {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "MEASURES")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "homemadeMeasure")
    @JsonIgnore
    private Set<Measure> measures;

    HomemadeMeasure() {
        measures = new HashSet<>();
    }

    public HomemadeMeasure(String name) {
        measures = new HashSet<>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Set<Measure> getMeasures() {
        return Collections.unmodifiableSet(measures);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HomemadeMeasure)) return false;

        HomemadeMeasure that = (HomemadeMeasure) o;

        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

}
