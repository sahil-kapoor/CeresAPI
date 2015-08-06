package com.ceres.domain.entity.parameter;

import com.ceres.domain.LocalDatePersistenceConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

/**
 * Created by leonardo on 21/03/15.
 */
@Entity
@DiscriminatorValue(value = "date")
@XmlRootElement(namespace = "ceres",name = "parameterDate")
public class ParameterDate extends ParameterValue<LocalDate> {

    @Column(name = "DATE_VALUE", columnDefinition = "DATE")
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate value;

    @Override
    public LocalDate getValue() {
        return value;
    }

    @Override
    public void setValue(LocalDate s) {
        this.value = s;
    }

    public ParameterDate() {
    }

    public ParameterDate(LocalDate date) {
        this.value = date;
    }

    public ParameterDate(Object date) {
        this.value = (LocalDate) date;
    }

}
