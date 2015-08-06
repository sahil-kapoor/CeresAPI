package com.ceres.domain;

import javax.persistence.AttributeConverter;
import java.sql.Time;
import java.time.LocalTime;

/**
 * Created by Leonardo on 25/03/15.
 */
public class LocalTimePersistenceConverter implements AttributeConverter<LocalTime, Time> {

    @Override
    public Time convertToDatabaseColumn(LocalTime entityValue) {
        return Time.valueOf(entityValue);
    }

    @Override
    public LocalTime convertToEntityAttribute(Time dbData) {
        return dbData.toLocalTime();
    }

}