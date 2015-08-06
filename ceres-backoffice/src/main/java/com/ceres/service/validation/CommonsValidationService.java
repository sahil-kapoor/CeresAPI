package com.ceres.service.validation;

/**
 * Created by leonardo on 19/04/15.
 */
public interface CommonsValidationService {

    boolean isEmailInUse(String email);

    boolean isTokenValid(String token);

}
