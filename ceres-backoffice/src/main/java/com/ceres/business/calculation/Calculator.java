package com.ceres.business.calculation;

import java.util.Collection;

/**
 * Created by leonardo on 10/03/15.
 */
public interface Calculator<T> {

    void calculate(T t);

    void calculateAll(Collection<T> t);

}
