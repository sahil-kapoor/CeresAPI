package com.ceres.domain.entity.human;

import com.ceres.domain.entity.menu.AbstractMenu;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by leonardo on 10/03/15.
 */
@Entity
@DiscriminatorValue("nutritionist")
@XmlRootElement(namespace = "ceres")
public class Nutritionist extends Human {

    @Column(nullable = true)
    private String crn;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE}, mappedBy = "nutritionist")
    @JsonIgnore
    private Set<AbstractMenu> menus = new HashSet<>();

    Nutritionist() {
    }


    public Set<AbstractMenu> getMenus() {
        return Collections.unmodifiableSet(menus);
    }

    public void addMenus(Set<AbstractMenu> menus) {
        this.menus.addAll(menus);
    }

    public void addMenu(AbstractMenu menu) {
        this.menus.add(menu);
    }

    public String getCrn() {
        return crn;
    }

    public void setCrn(String crn) {
        this.crn = crn;
    }

    @Override
    public String toString() {
        return "Nutritionist{" + this.getName().toString() +
                "crn='" + crn + '\'' +
                '}';
    }

}
