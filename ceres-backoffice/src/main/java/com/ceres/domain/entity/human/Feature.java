package com.ceres.domain.entity.human;

import com.ceres.domain.LocalDatePersistenceConverter;
import com.ceres.domain.entity.AbstractEntity;
import com.ceres.domain.entity.parameter.Parameter;
import com.ceres.domain.entity.parameter.ParameterValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;


/**
 * Created by leonardo on 10/03/15.
 */
@Entity
@Table(name = "HUMAN_FEATURE",
        indexes = {@Index(name = "DATE_INDEX", columnList = "DATE"),
                @Index(name = "HUMAN_INDEX", columnList = "HUMAN_ID"),
                @Index(name = "FEATURE_ID", columnList = "ID")})
@XmlRootElement(namespace = "ceres")
@XmlAccessorType(XmlAccessType.PROPERTY)
@ApiModel(value = "Consulta")
public class Feature extends AbstractEntity implements Comparable<Feature> {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "HUMAN_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_HUMAN_ID"))
    @JsonIgnore
    private Patient patient;

    @Column(name = "DATE", nullable = false, columnDefinition = "DATE NOT NULL")
    @Convert(converter = LocalDatePersistenceConverter.class)
    @ApiModelProperty(value = "visitDate", example = "dd-mm-aaaa")
    private LocalDate visitDate;

    @OneToMany(mappedBy = "feature", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @MapKey
    @JsonIgnore
    private Map<Parameter, ParameterValue> parameters = new HashMap<>();

    @Transient
    @ApiModelProperty(value = "featureParameters", example = "key:value")
    private Map<String, Object> featureParameters = new LinkedHashMap<>();

    @Transient
    @ApiModelProperty(value = "calcResults", example = "key:value")
    private Map<String, Double> calcResults = new LinkedHashMap<>();

    public Feature() {
        getFeatureParameters();
    }

    public void addParameter(ParameterValue parameter) {
        parameter.setFeature(this);
        this.parameters.put(parameter.getParameter(), parameter);
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public Map<Parameter, ParameterValue> getParameters() {
        return this.parameters;
    }

    public Map<String, Object> getFeatureParameters() {
        if (featureParameters.isEmpty() && !parameters.isEmpty()) {
            this.parameters.values().forEach(k -> featureParameters.put(k.getParameter().getMnemonic(), k.getValue()));
        }

        return featureParameters;
    }

    public void addFeatureParameters(String key, Double value) {
        this.featureParameters.put(key, value);
    }

    public Map<String, Double> getCalcResults() {
        return calcResults;
    }

    public void addCalcResult(String name, Double result) {
        this.calcResults.put(name, result);
    }

    @Override
    public int compareTo(Feature o) {
        if (visitDate != null && o.getVisitDate() != null)
            return this.visitDate.compareTo(o.getVisitDate());
        else
            return 0;
    }

    @Override
    public String toString() {
        return "Feature{" +
                " visitDate=" + visitDate +
                ", patient=" + patient +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feature feature = (Feature) o;
        return Objects.equals(getPatient(), feature.getPatient()) &&
                Objects.equals(getVisitDate(), feature.getVisitDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPatient(), getVisitDate());
    }
}
