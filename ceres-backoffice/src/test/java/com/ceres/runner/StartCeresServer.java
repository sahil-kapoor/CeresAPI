package com.ceres.runner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


/**
 * Servidor para ambiente de desenvolvimento. Sobe o container e o banco embarcados.
 * <p>
 * Created by Leonardo on 19/01/2015.
 */
@ComponentScan({"com.ceres.application", "com.ceres.business", "com.ceres.controller", "com.ceres.service", "com.ceres.load"})
@EntityScan({"com.ceres.domain.entity"})
@EnableJpaRepositories({"com.ceres.domain.repository"})
@EnableAutoConfiguration
public class StartCeresServer {

    public static void main(final String[] args) {
        SpringApplication ceresServer = new SpringApplication(StartCeresServer.class);
        ceresServer.setShowBanner(true);
        ceresServer.run(args);
    }

}
