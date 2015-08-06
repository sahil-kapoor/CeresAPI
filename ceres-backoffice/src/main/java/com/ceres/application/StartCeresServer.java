package com.ceres.application;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


/**
 * Created by Leonardo on 19/01/2015.
 */
@ComponentScan(basePackages = "com.ceres")
@EntityScan(basePackages = "com.ceres.domain.entity")
@EnableJpaRepositories(basePackages = "com.ceres.domain.repository")
@SpringBootApplication
public class StartCeresServer extends SpringBootServletInitializer {

    /**
     * Builder da aplicação, diz ao framework quais são os artefatos que devem ser carregados
     *
     * @param applicationBuilder a aplicação que está sendo carregada
     * @return Sources que devem ser agregados
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
        return applicationBuilder.sources(StartCeresServer.class);
    }

}
