package com.ceres.application.configuration;

import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by Leonardo on 21/01/2015.
 */
@Configuration
@Profile("DEV")
public class ConsoleH2Config {

    @Value("${h2.context-path}")
    private String path;

    @Bean
    public ServletRegistrationBean h2servletRegistration() {
        WebServlet webServlet = new WebServlet();
        ServletRegistrationBean registration = new ServletRegistrationBean(webServlet);
        registration.setName("h2Console");
        registration.getInitParameters().put("webAllowOthers", "true");
        registration.addUrlMappings(path);
        return registration;
    }

}
