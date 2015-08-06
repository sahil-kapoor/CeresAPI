package com.ceres.domain.entity.human;

import com.ceres.domain.Builder;
import com.ceres.domain.embedabble.Address;
import com.ceres.domain.embedabble.Contact;
import com.ceres.domain.embedabble.Name;
import com.ceres.domain.entity.taco.Food;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by leonardo on 15/03/15.
 */
public class PatientBuilder implements Builder<Patient> {

    private String cpf;
    private Name name;
    private LocalDate birthdate;
    private Gender gender;
    private Contact contact;
    private Address address;
    private SortedSet<Feature> features = new TreeSet<>();
    private Set<Food> preferences = new HashSet<>();
    private Set<Food> restrictions = new HashSet<>();

    private String password;

    public PatientBuilder setName(Name name) {
        this.name = name;
        return this;
    }

    public PatientBuilder setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public PatientBuilder setContact(Contact contact) {
        this.contact = contact;
        return this;
    }

    public PatientBuilder setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public PatientBuilder setAddress(Address address) {
        this.address = address;
        return this;
    }

    public PatientBuilder addAllFeatures(SortedSet<Feature> features) {
        this.features.addAll(features);
        return this;
    }

    public PatientBuilder addFeature(Feature feature) {
        this.features.add(feature);
        return this;
    }

    public PatientBuilder addAllRestrictions(Set<Food> foods) {
        this.restrictions.addAll(foods);
        return this;
    }

    public PatientBuilder addRestriction(Food food) {
        this.restrictions.add(food);
        return this;
    }

    public PatientBuilder addAllPreferences(Set<Food> foods) {
        this.preferences.addAll(foods);
        return this;
    }

    public PatientBuilder addPreference(Food food) {
        this.preferences.add(food);
        return this;
    }

    public PatientBuilder setPassword(String passwd) {
        this.password = passwd;
        return this;
    }

    public PatientBuilder setCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    @Override
    public Patient build() {
        Patient patient = new Patient();
        patient.setCpf(cpf);
        patient.setAddress(address);
        patient.setContact(contact);
        patient.setGender(gender);
        patient.setName(name);
        patient.setBirthdate(birthdate);

        this.features.forEach(patient::addFeature);
        this.restrictions.forEach(patient::addRestriction);
        this.preferences.forEach(patient::addPreference);

        return patient;
    }
}
