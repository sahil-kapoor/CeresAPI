package com.ceres.domain.entity.menu;

import com.ceres.domain.Builder;
import com.ceres.domain.entity.human.Nutritionist;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class MenuTemplateBuilder implements Builder<MenuTemplate> {

    private LocalDate creationDate;
    private String name;
    private String mnemonic;
    private Nutritionist nutritionist;
    private Set<MenuContent> menuContent = new HashSet<>();

    public MenuTemplateBuilder setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public MenuTemplateBuilder setNutritionist(Nutritionist nutri) {
        this.nutritionist = nutri;
        return this;
    }


    public MenuTemplateBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public MenuTemplateBuilder addMenuContent(MenuContent menuContent) {
        this.menuContent.add(menuContent);
        return this;
    }

    public MenuTemplateBuilder addAllMenuContent(Set<MenuContent> menuContents) {
        this.menuContent.addAll(menuContents);
        return this;
    }

    @Override
    public MenuTemplate build() {
        MenuTemplate menu = new MenuTemplate();
        menu.setName(this.name);
        menu.setNutritionist(this.nutritionist);
        menu.setCreationDate(this.creationDate);

        this.menuContent.forEach(menu::addMeal);

        return menu;
    }
}