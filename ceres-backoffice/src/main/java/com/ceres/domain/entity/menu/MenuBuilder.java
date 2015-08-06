package com.ceres.domain.entity.menu;

import com.ceres.domain.Builder;
import com.ceres.domain.entity.human.Nutritionist;
import com.ceres.domain.entity.human.Patient;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class MenuBuilder implements Builder<Menu> {

    private LocalDate creationDate;
    private LocalDate validityDate;
    private String name;
    private Patient patient;
    private Nutritionist nutritionist;
    private Boolean isTemplate = false;
    private MenuTemplate menuTemplate;
    private Set<MenuContent> menuContent = new HashSet<>();

    public MenuBuilder setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public MenuBuilder setValidityDate(LocalDate validityDate) {
        this.validityDate = validityDate;
        return this;
    }

    public MenuBuilder setPatient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public MenuBuilder setNutritionist(Nutritionist nutri) {
        this.nutritionist = nutri;
        return this;
    }

    public MenuBuilder setTemplate(MenuTemplate template) {
        this.menuTemplate = template;
        return this;
    }

    public MenuBuilder setCloneAsTemplate(boolean template) {
        this.isTemplate = template;
        return this;
    }

    public MenuBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public MenuBuilder withTemplate(Boolean template) {
        this.isTemplate = template;
        return this;
    }

    public MenuBuilder addMenuContent(MenuContent menuContent) {
        this.menuContent.add(menuContent);
        return this;
    }

    public MenuBuilder addAllMenuContent(Set<MenuContent> menuContents) {
        this.menuContent.addAll(menuContents);
        return this;
    }

    @Override
    public Menu build() {
        Menu menu = new Menu();
        menu.setPatient(this.patient);
        menu.setName(this.name);
        menu.setNutritionist(this.nutritionist);
        menu.setCreationDate(this.creationDate);
        menu.setValidityDate(this.validityDate);
        menu.setTemplate(this.menuTemplate);
        menu.setCloneAsTemplate(isTemplate);

        this.menuContent.forEach(menu::addMeal);

        return menu;
    }
}