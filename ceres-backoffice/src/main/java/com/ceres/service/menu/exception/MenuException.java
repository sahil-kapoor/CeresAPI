package com.ceres.service.menu.exception;

/**
 * Created by leonardo on 29/03/15.
 */
public class MenuException extends Exception {

    private String message;

    public MenuException(String message) {
        super(message);
        this.message = message;
    }

    public MenuException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
