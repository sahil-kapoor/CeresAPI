package com.ceres.domain.entity.menu;

import com.ceres.domain.LocalDatePersistenceConverter;
import com.ceres.domain.entity.human.Patient;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by leonardo on 14/03/15.
 */
@Entity
@Table(name = "MENU_MODEL", indexes = {
        @Index(name = "CREATION_DATE_INDEX", columnList = "CREATION_DATE", unique = false),
        @Index(name = "VALIDITY_DATE_INDEX", columnList = "VALIDITY_DATE", unique = false)})
@ApiModel(value = "menu", parent = AbstractMenu.class)
@XmlRootElement(namespace = "ceres")
public class Menu extends AbstractMenu {

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "HUMAN_PATIENT_ID", foreignKey = @ForeignKey(name = "FK_MENU_PATIENT_ID"))
    @JsonIgnore
    private Patient patient;

    @Column(name = "CREATION_DATE", nullable = false, columnDefinition = "DATE NOT NULL")
    @Convert(converter = LocalDatePersistenceConverter.class)
    @ApiModelProperty(value = "creationDate", example = "dd-mm-aaaa")
    private LocalDate creationDate;

    @Column(name = "VALIDITY_DATE", nullable = false, columnDefinition = "DATE NOT NULL")
    @Convert(converter = LocalDatePersistenceConverter.class)
    @ApiModelProperty(value = "validityDate", example = "dd-mm-aaaa")
    private LocalDate validityDate;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "MENU_TEMPLATE_ID", unique = false, nullable = true, foreignKey = @ForeignKey(name = "FK_MENU_TEMPLATE_ID"))
    @JsonBackReference
    private MenuTemplate template;

    @Transient
    @JsonIgnore
    private Boolean cloneAsTemplate = false;

    public Boolean isTemplate() {
        return this.cloneAsTemplate;
    }

    public MenuTemplate makeTemplate(String menuName) {

        Set<MenuContent> menuContent = new HashSet<>();

        try {
            for (MenuContent content : this.getMeals()) {
                menuContent.add((MenuContent) content.clone());
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace(); //TODO
        }

        return new MenuTemplateBuilder()
                .withName(menuName)
                .setNutritionist(this.getNutritionist())
                .setCreationDate(this.getCreationDate())
                .addAllMenuContent(menuContent)
                .build();

    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDate getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(LocalDate validityDate) {
        this.validityDate = validityDate;
    }

    protected void cloneAsTemplate(Boolean condition) {
        this.cloneAsTemplate = cloneAsTemplate;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public MenuTemplate getTemplate() {
        return template;
    }

    public void setTemplate(MenuTemplate template) {
        this.template = template;
    }

    public Boolean getCloneAsTemplate() {
        return cloneAsTemplate;
    }

    public void setCloneAsTemplate(Boolean cloneAsTemplate) {
        this.cloneAsTemplate = cloneAsTemplate;
    }

    @Override
    public String toString() {
        return this.getName();
    }


}
