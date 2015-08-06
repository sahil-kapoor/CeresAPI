package com.ceres.domain.entity.human;

import com.wordnik.swagger.annotations.ApiModel;

/**
 * Created by leonardo on 10/03/15.
 */
@ApiModel(value = "Tipo Usuário")
public enum UserCategory {

    PATIENT(0), NUTRITIONIST(1), ADMIN(2);

    private int userType;

    UserCategory(int userType) {
        this.userType = userType;
    }

    public static UserCategory getCategoryOf(Object object) {

        if (object instanceof Patient)
            return PATIENT;
        else
            return NUTRITIONIST;

    }

}
