package com.ceres.application.configuration;

import com.wordnik.swagger.jaxrs.config.BeanConfig;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

/**
 * Created by leonardo on 21/04/15.
 */
@Configuration
public class SwaggerConfig extends SpringBootServletInitializer {

    public SwaggerConfig() {
        super();
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setTitle("Ceres");
        beanConfig.setBasePath("/api/");
        beanConfig.setHost("ceresapp.com");
        beanConfig.setDescription("API de serviços do projeto Ceres");
        beanConfig.setContact("leo.rog@gmail.com");
        beanConfig.setResourcePackage("com.ceres.controller");
        beanConfig.setScan(true);
    }
}