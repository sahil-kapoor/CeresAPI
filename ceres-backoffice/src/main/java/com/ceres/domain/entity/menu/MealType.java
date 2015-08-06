package com.ceres.domain.entity.menu;

/**
 * Created by Leonardo on 25/02/2015.
 */
public enum MealType {

    BREAKFAST(1), BEFORE_LUNCH(2), LUNCH(3), AFTER_LUNCH(4), BEFORE_DINNER(5), DINNER(6);

    private int i;

    MealType(int i) {
        this.i = i;
    }

}
