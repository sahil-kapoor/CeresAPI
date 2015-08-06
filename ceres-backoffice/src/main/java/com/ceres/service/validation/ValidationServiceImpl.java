package com.ceres.service.validation;

import com.ceres.service.user.UserService;
import com.ceres.service.user.exception.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by leonardo on 19/04/15.
 */
@Service
public class ValidationServiceImpl implements CommonsValidationService {

    private UserService userService;

    @Inject
    public ValidationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isEmailInUse(String email) {
        return userService.existsByUsername(email);
    }

    @Override
    public boolean isTokenValid(String token) {
        try {
            userService.validate(token);
            return true;
        } catch (AuthenticationException e) {
            return false;
        }

    }
}
