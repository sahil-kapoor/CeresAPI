package com.ceres.domain.entity.human;

import com.ceres.domain.embedabble.Address;
import com.ceres.domain.entity.menu.Menu;
import com.ceres.domain.entity.taco.Food;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wordnik.swagger.annotations.ApiModel;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

/**
 * Created by leonardo on 10/03/15.
 */
@Entity
@DiscriminatorValue("patient")
@ApiModel(value = "Paciente", parent = Human.class, description = "Representaçao de um paciente")
@XmlRootElement(namespace = "ceres")
public class Patient extends Human {

    @Column(nullable = true, unique = true)
    private String cpf;

    @Embedded
    private Address address;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE}, mappedBy = "patient")
    @JsonIgnore
    private Set<Menu> menus = new HashSet<>();

    @Column(nullable = false)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "patient", orphanRemoval = true, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @OrderBy(value = "visitDate DESC")
    @JsonIgnore
    private SortedSet<Feature> features = new TreeSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @org.hibernate.annotations.ForeignKey(name = "FK_RESTRICTION_HUMAN_ID", inverseName = "FK_RESTRICTION_FOOD_ID")
    @JoinTable(name = "HUMAN_RESTRICTION",
            joinColumns = {@JoinColumn(name = "HUMAN_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "FOOD_ID", referencedColumnName = "ID")}
    )
    @JsonIgnore
    private Set<Food> restrictions = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @org.hibernate.annotations.ForeignKey(name = "FK_PREFERENCE_HUMAN_ID", inverseName = "FK_PREFERENCE_FOOD_ID")
    @JoinTable(
            name = "HUMAN_PREFERENCE",
            joinColumns = {@JoinColumn(name = "HUMAN_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "FOOD_ID", referencedColumnName = "ID")}
    )
    @JsonIgnore
    private Set<Food> preferences = new HashSet<>();

    Patient() {
    }

    public SortedSet<Feature> getFeatures() {
        return Collections.unmodifiableSortedSet(features);
    }

    public void removeFeature(Feature f) {
        this.features.remove(f);
    }

    public void addFeature(Feature feature) {
        feature.setPatient(this);
        this.features.add(feature);
    }

    public void addRestriction(Food food) {
        this.restrictions.add(food);
    }

    public void addPreference(Food food) {
        this.preferences.add(food);
    }

    public void removeRestriction(Food food) {
        this.restrictions.remove(food);
    }

    public void removePreference(Food food) {
        this.preferences.remove(food);
    }

    public void addAllRestriction(List<Food> food) {
        this.restrictions.addAll(food);
    }

    public void addAllPreference(List<Food> food) {
        this.preferences.addAll(food);
    }

    public void removeAllRestriction(List<Food> food) {
        this.restrictions.removeAll(food);
    }

    public void removeAllPreference(List<Food> food) {
        this.preferences.removeAll(food);
    }

    public Set<Food> getPreferences() {
        return Collections.unmodifiableSet(preferences);
    }

    public Set<Food> getRestrictions() {
        return Collections.unmodifiableSet(restrictions);
    }


    public Address getAddress() {
        return address;
    }

    public Set<Menu> getMenus() {
        return Collections.unmodifiableSet(menus);
    }

    public void addMenu(Menu menu) {
        this.menus.add(menu);
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
