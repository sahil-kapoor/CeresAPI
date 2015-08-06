package com.ceres.domain.entity.menu;

import com.ceres.domain.entity.AbstractEntity;
import com.ceres.domain.entity.human.Nutritionist;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.wordnik.swagger.annotations.ApiModel;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by leonardo on 21/03/15.
 */
@Entity
@Table(name = "MENU")
@Inheritance(strategy = InheritanceType.JOINED)
@ApiModel(value = "abstractMenu", subTypes = {Menu.class, MenuTemplate.class})
@XmlRootElement(namespace = "ceres")
public abstract class AbstractMenu extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "HUMAN_NUTRITIONIST_ID", foreignKey = @ForeignKey(name = "FK_MENU_NUTRITIONIST_ID"))
    @JsonIgnore
    private Nutritionist nutritionist;

    @OneToMany(mappedBy = "menu", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JsonManagedReference
    @JsonIgnore
    private Set<MenuContent> meals = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Nutritionist getNutritionist() {
        return nutritionist;
    }

    public void setNutritionist(Nutritionist nutritionist) {
        this.nutritionist = nutritionist;
    }

    public Set<MenuContent> getMeals() {
        return Collections.unmodifiableSet(meals);
    }

    public void addMeal(MenuContent content) {
        content.setMenu(this);
        this.meals.add(content);
    }

    public void addAllMeal(Set<MenuContent> content) {
        content.forEach(k -> {
            k.setMenu(this);
            this.meals.add(k);
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity that = (AbstractEntity) o;

        if (getId() == null && that.getId() == null) return true;

        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
