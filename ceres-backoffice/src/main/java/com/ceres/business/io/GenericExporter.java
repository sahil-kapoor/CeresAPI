package com.ceres.business.io;

import com.ceres.domain.entity.AbstractEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by leonardo on 19/04/15.
 */
@Named(value = "GenericExporter")
public class GenericExporter implements Exporter<AbstractEntity> {

    private static final String FILE_TYPE = ".xml";

    private ObjectMapper xmlMapper;

    private static final Logger logger = LoggerFactory.getLogger(GenericExporter.class);

    @PostConstruct
    protected void init() {
        xmlMapper = new XmlMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setDateFormat(new SimpleDateFormat("dd-MM-yyyy"))
                .findAndRegisterModules();
    }

    @Override
    public File doExport(String fileName, AbstractEntity abstractEntity) {
        return createAndWrite(fileName, abstractEntity);
    }

    private File createAndWrite(String name, Object obj) {
        File file = new File(name + FILE_TYPE);
        try {
            xmlMapper.writeValue(file, obj);
        } catch (IOException e) {
            logger.error("Cannot create file from object ", e);
        }
        return file;
    }

}


