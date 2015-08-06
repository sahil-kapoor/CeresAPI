package com.ceres.service.menu.exception;

import com.ceres.util.MessageCreator;
import com.ceres.util.MessageResolver;

import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Created by leonardo on 29/03/15.
 */
public enum MenuExceptionMessages implements MessageCreator {

    INVALID_PARAMETER("feature.invalid.parameter");

    private static final String TARGET_BUNDLE = "error-messages";
    private String message;

    MenuExceptionMessages(String message) {
        this.message = message;
    }

    @Override
    public String create(String... args) {
        String bundleMessage = ResourceBundle.getBundle(TARGET_BUNDLE).getString(this.message);
        return MessageResolver.resolve(bundleMessage, Arrays.asList(args));
    }

    public String getMessage() {
        return message;
    }

}
