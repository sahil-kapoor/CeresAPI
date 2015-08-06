package com.ceres.application.configuration;

import com.wordnik.swagger.jaxrs.listing.ApiListingResource;
import com.wordnik.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;

/**
 * Created by Leonardo on 20/01/2015.
 */
@Configuration
@ApplicationPath(value = "/api")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        packages(true, "com.ceres");

//        register(AuthenticationFilter.class);
//        register(RolesAllowedDynamicFeature.class);
        register(ApiListingResource.class);
        register(SwaggerSerializers.class);

    }

}