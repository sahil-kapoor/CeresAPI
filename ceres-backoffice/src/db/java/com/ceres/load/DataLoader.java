package com.ceres.load;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

/**
 * Created by leonardo on 17/03/15.
 */
@Component
@Profile("DEV")
public class DataLoader {

    @Value("${dataload.programatically}")
    private Boolean shouldLoad;

    @Inject
    private List<DataLoad> loaders;

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    @PostConstruct
    public void init() {
        Collections.sort(loaders, AnnotationAwareOrderComparator.INSTANCE);

        logger.info("Starting dataloader....");
        try {
            if (shouldLoad) {
                loaders.forEach(DataLoad::load);
            }
        } catch (Exception ex) {
            logger.error("Failed to execute dataloader....", ex);
        }
    }
}
