package com.ceres.service.human.exception;

/**
 * Created by leonardo on 29/03/15.
 */
public class FeatureException extends Exception {

    private String message;

    public FeatureException(String message) {
        super(message);
        this.message = message;
    }

    public FeatureException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
