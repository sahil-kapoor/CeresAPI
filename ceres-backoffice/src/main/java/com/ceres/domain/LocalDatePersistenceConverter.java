package com.ceres.domain;

import javax.persistence.AttributeConverter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by leonardo on 14/03/15.
 */
public class LocalDatePersistenceConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public java.sql.Date convertToDatabaseColumn(LocalDate entityValue) {
        return java.sql.Date.valueOf(entityValue);
    }

    @Override
    public LocalDate convertToEntityAttribute(Date dbData) {
        return dbData.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}