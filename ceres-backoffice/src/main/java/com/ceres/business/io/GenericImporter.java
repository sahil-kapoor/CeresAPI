package com.ceres.business.io;


import com.ceres.business.validation.Validator;
import com.ceres.domain.entity.AbstractEntity;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by leonardo on 19/04/15.
 */
@Named(value = "GenericImporter")
public class GenericImporter implements Importer<AbstractEntity> {

    private Validator<File> fileValidator;

    private ObjectMapper xmlMapper;

    private static final Logger logger = LoggerFactory.getLogger(GenericImporter.class);

    @Inject
    public GenericImporter(Validator<File> fileValidator) {
        this.fileValidator = fileValidator;
    }

    @PostConstruct
    protected void init() {
        xmlMapper = new XmlMapper()
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                .configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)
                .findAndRegisterModules();
    }

    @Override
    public AbstractEntity doImport(File file, Class<AbstractEntity> target) {
        String content = "";

        if (fileValidator.isValid(file)) {
            throw new RuntimeException("Invalid file format");
        }

        try {
            content = getContentLine(file, target.getSimpleName().toLowerCase());
            return xmlMapper.readValue(content, target);
        } catch (IOException e) {
            logger.error("Error while parsing XML " + content, e);
            throw new RuntimeException();
        }
    }

    private String getContentLine(File file, String targetClassName) throws IOException {
        String content = "";
        int iniPos = 0;
        int lastPos = 0;
        try {
            content = new String(Files.readAllBytes(Paths.get(file.getPath())));
        } catch (IOException e) {
            logger.error("Cannot read file " + file.getName(), e);
        }

        if (content.contains(targetClassName)) {
            iniPos = content.indexOf(targetClassName) - 1;
            lastPos = content.lastIndexOf(targetClassName) + targetClassName.length() + 1;
        } else {
            throw new IOException("Incompatible File");
        }

        return content.substring(iniPos, lastPos).replace("\n", "");
    }

}


