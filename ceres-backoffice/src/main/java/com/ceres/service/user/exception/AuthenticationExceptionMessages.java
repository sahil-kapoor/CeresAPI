package com.ceres.service.user.exception;

import com.ceres.util.MessageCreator;
import com.ceres.util.MessageResolver;

import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Created by leonardo on 26/03/15.
 */
public enum AuthenticationExceptionMessages implements MessageCreator {

    INVALID_REQUEST("authentication.invalid.request"),
    INVALID_PASSWORD("authentication.invalid.password"),
    INVALID_CREDENTIAL("authentication.invalid.credential"),
    INVALID_TOKEN("authentication.invalid.token");

    private static final String TARGET_BUNDLE = "error-messages";
    private String message;

    AuthenticationExceptionMessages(String message) {
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
