package com.ceres.domain.entity.human;

import com.wordnik.swagger.annotations.ApiModel;

/**
 * Created by leonardo on 10/03/15.
 */
@ApiModel(value = "Gênero")
public enum Gender {

    MALE("MASCULINO"), FEMALE("FEMININO");

    private final String genderText;

    Gender(String genderText) {
        this.genderText = genderText;
    }

    public String getName() {
        return genderText;
    }
}
