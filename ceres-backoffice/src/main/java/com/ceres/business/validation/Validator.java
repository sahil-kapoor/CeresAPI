package com.ceres.business.validation;

/**
 * Created by leonardo on 02/05/15.
 */
public interface Validator<T> {

    boolean isValid(T t);

}
