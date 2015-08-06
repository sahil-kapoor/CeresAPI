package com.ceres.service.human.exception;

/**
 * Created by leonardo on 29/03/15.
 */
public class HumanException extends Exception {

    private String message;

    public HumanException(String message) {
        super(message);
        this.message = message;
    }

    public HumanException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
