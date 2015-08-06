package com.ceres.domain.embedabble;

import com.ceres.domain.entity.taco.MeasureUnit;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by leonardo on 08/03/15.
 */
@Embeddable
@XmlRootElement(namespace = "ceres")
public class Portion implements Serializable {

    /**
     * ATRIBUTOS ESTÃO NULLABLE PARA NAO QUEBRAR OS 600 INSERTS DE ALIMENTO
     */

    // TODO Arrumar inserts inserindo a unidade de medida e seu valor de porção

    @Column(nullable = true, precision = 2, name = "PORTION_VALUE")
    private Double value;

    @Column(nullable = true, name = "MEASURE_UNIT")
    @Enumerated(value = EnumType.STRING)
    private MeasureUnit measureUnit;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public MeasureUnit getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(MeasureUnit measureUnit) {
        this.measureUnit = measureUnit;
    }

    public Portion() {
    }

    public Portion(Double value, MeasureUnit measureUnit) {
        this.value = value;
        this.measureUnit = measureUnit;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
