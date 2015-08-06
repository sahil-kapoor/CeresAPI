package com.ceres.application.filter;

import com.ceres.application.configuration.CeresSecurityContext;
import com.ceres.domain.entity.human.SystemUser;
import com.ceres.service.user.UserService;
import com.ceres.service.user.exception.AuthenticationException;
import org.springframework.context.annotation.Scope;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;

/**
 * Created by leonardo on 22/02/15.
 */
@Named("AuthenticationFilter")
@Scope("prototype")
@PreMatching
@Priority(Priorities.AUTHORIZATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String AUTHENTICATE = "WWW-Authenticate";

    private UserService userService;

    @Inject
    public AuthenticationFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();
        String auth = requestContext.getHeaderString(AUTHORIZATION_HEADER);

        if (path.contains("/login") || path.contains("/recursos")) {
            return;
        }

        try {
            userService.validate(auth);
        } catch (AuthenticationException e) {
            requestContext.abortWith(Response
                    .status(Response.Status.UNAUTHORIZED)
                    .header(AUTHENTICATE, "Basic")
                    .build());
            return;
        }

        SystemUser systemUser = userService.findUserByToken(auth);
        fillSecurityContext(requestContext, systemUser);
    }

    private void fillSecurityContext(ContainerRequestContext requestContext, SystemUser user) {
        SecurityContext securityContext = new CeresSecurityContext(user);
        requestContext.setSecurityContext(securityContext);
    }

}
