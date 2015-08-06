package com.ceres.domain.entity.script;

import com.ceres.domain.entity.AbstractEntity;
import com.ceres.domain.entity.human.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by leonardo on 18/03/15.
 */
@Entity
@Table(name = "SCRIPT", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"MNEMONIC", "TARGET_GENDER"}, name = "UK_MNEMONIC_TARGET_GENDER")
})
@ApiModel(value = "script")
@XmlRootElement(namespace = "ceres")
public class Script extends AbstractEntity implements Comparable<Script> {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String mnemonic;

    @Column(nullable = true, name = "TARGET_GENDER")
    @Enumerated(value = EnumType.ORDINAL)
    private Gender targetGender;

    @Basic(fetch = FetchType.EAGER)
    @Column(nullable = false, columnDefinition = "TEXT")
    private String scriptContent;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    @org.hibernate.annotations.ForeignKey(name = "FK_SCRIPT_ID", inverseName = "FK_SCRIPT_DEPENDENCY_ID")
    @JoinTable(
            name = "SCRIPT_DEPENDENCY",
            joinColumns = @JoinColumn(name = "SCRIPT_ID", referencedColumnName = "ID", nullable = false, unique = false),
            inverseJoinColumns = @JoinColumn(name = "DEPENDENCY_ID", referencedColumnName = "ID", nullable = false, unique = false)
    )
    @XmlElementWrapper(name = "dependencies")
    private Set<Script> dependencies = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORY_ID", foreignKey = @ForeignKey(name = "FK_SCRIPT_CATEGORY"))
    @JsonIgnoreProperties(value = {"scripts", "name"})
    @ApiModelProperty(value = "category", dataType = "Long")
    private ScriptCategory category;

    public Gender getTargetGender() {
        return targetGender;
    }

    public void setTargetGender(Gender gender) {
        this.targetGender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScriptContent() {
        return scriptContent;
    }

    public void setScriptContent(String scriptContent) {
        this.scriptContent = scriptContent;
    }

    public ScriptCategory getCategory() {
        return category;
    }

    public void setCategory(ScriptCategory category) {
        this.category = category;
    }

    public Set<Script> getDependencies() {
        return Collections.synchronizedSet(dependencies);
    }

    public void addDependency(Script dependency) {
        this.dependencies.add(dependency);
    }

    public void addAllDependency(Set<Script> dependencies) {
        this.dependencies.addAll(dependencies);
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public boolean hasDependencies() {
        return this.getDependencies().size() > 0;
    }

    @JsonIgnore
    public Set<String> getDependencyMnemonics() {
        final Set<String> dependencyMnemonics = new HashSet<>();
        this.dependencies.forEach(k -> dependencyMnemonics.add(k.getMnemonic()));
        return dependencyMnemonics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Script script = (Script) o;
        return Objects.equals(getMnemonic(), script.getMnemonic()) &&
                Objects.equals(getTargetGender(), script.getTargetGender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMnemonic(), getTargetGender());
    }

    @Override
    public String toString() {
        return mnemonic;
    }

    @Override
    public int compareTo(Script o) {
        Integer thisDep = dependencies.size();
        Integer targetDep = o.getDependencyMnemonics().size();

        return thisDep.compareTo(targetDep);
    }
}
