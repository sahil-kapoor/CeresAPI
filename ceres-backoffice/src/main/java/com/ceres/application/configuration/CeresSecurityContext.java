package com.ceres.application.configuration;

import com.ceres.domain.entity.human.SystemUser;
import com.ceres.domain.entity.human.UserCategory;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

/**
 * Created by leonardo on 23/02/15.
 */
public class CeresSecurityContext implements SecurityContext {

    private SystemUser user;

    public CeresSecurityContext(SystemUser user) {
        this.user = user;
    }

    @Override
    public Principal getUserPrincipal() {
        return user;
    }

    @Override
    public boolean isUserInRole(String role) {
        return user.getRole().equals(UserCategory.valueOf(role));
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return null;
    }


}
