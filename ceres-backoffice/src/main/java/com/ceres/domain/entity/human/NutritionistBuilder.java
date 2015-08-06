package com.ceres.domain.entity.human;

import com.ceres.domain.Builder;
import com.ceres.domain.embedabble.Contact;
import com.ceres.domain.embedabble.Name;

import java.time.LocalDate;

/**
 * Created by leonardo on 15/03/15.
 */
public class NutritionistBuilder implements Builder<Nutritionist> {

    private Name name;
    private Gender gender;
    private LocalDate birthdate;
    private Contact contact;
    private String password;
    private String crn;


    public NutritionistBuilder setName(Name name) {
        this.name = name;
        return this;
    }

    public NutritionistBuilder setContact(Contact contact) {
        this.contact = contact;
        return this;
    }

    public NutritionistBuilder setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public NutritionistBuilder setPassword(String passwd) {
        this.password = passwd;
        return this;
    }


    public NutritionistBuilder setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public NutritionistBuilder setCRN(String crn) {
        this.crn = crn;
        return this;
    }

    @Override
    public Nutritionist build() {
        Nutritionist nutri = new Nutritionist();
        nutri.setGender(gender);
        nutri.setBirthdate(birthdate);
        nutri.setName(name);
        nutri.setCrn(crn);
        nutri.setContact(contact);
        return nutri;
    }
}
