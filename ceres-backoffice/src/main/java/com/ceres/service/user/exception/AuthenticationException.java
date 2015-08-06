package com.ceres.service.user.exception;

/**
 * Created by leonardo on 26/03/15.
 */
public class AuthenticationException extends Exception {

    private String message;

    public AuthenticationException(String message) {
        super(message);
        this.message = message;
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
