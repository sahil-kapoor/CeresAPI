package com.ceres.domain.entity.script;

import com.ceres.domain.entity.AbstractEntity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

/**
 * Created by leonardo on 18/03/15.
 */
@Entity
@Table(name = "SCRIPT_CATEGORY")
@XmlRootElement(namespace = "ceres")
public class ScriptCategory extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Script> scripts = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Script> getScripts() {
        return Collections.unmodifiableList(scripts);
    }

    public void addScripts(SortedSet<Script> scripts) {
        this.scripts.addAll(scripts);
    }

    public void addScript(Script script) {
        this.scripts.add(script);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScriptCategory that = (ScriptCategory) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
