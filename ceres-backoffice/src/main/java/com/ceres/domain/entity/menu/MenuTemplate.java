package com.ceres.domain.entity.menu;

import com.ceres.domain.LocalDatePersistenceConverter;
import com.ceres.domain.entity.human.Patient;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Leonardo on 17/03/2015.
 */
@Entity
@Table(name = "MENU_TEMPLATE")
@ApiModel(value = "menuTemplate", parent = AbstractMenu.class)
@XmlRootElement(namespace = "ceres")
public class MenuTemplate extends AbstractMenu {

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "template")
    @JsonManagedReference
    private Set<Menu> menus = new HashSet<>();

    @Column(name = "CREATION_DATE", nullable = false, columnDefinition = "DATE NOT NULL")
    @Convert(converter = LocalDatePersistenceConverter.class)
    @ApiModelProperty(value = "creationDate", example = "dd-mm-aaaa")
    private LocalDate creationDate;

    public Menu makeMenu(Patient patient, LocalDate validityDate) {
        Set<MenuContent> menuContent = new HashSet<>();
        try {
            for (MenuContent content : this.getMeals()) {
                menuContent.add((MenuContent) content.clone());
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace(); //TODO
        }

        return new MenuBuilder()
                .withName(this.getName())
                .setNutritionist(this.getNutritionist())
                .setCreationDate(LocalDate.now())
                .setTemplate(this)
                .addAllMenuContent(menuContent)
                .setPatient(patient)
                .setValidityDate(validityDate)
                .build();
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return this.getName();
    }

}
