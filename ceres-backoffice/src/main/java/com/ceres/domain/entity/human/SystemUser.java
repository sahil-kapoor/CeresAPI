package com.ceres.domain.entity.human;

import com.ceres.domain.entity.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wordnik.swagger.annotations.ApiModel;

import javax.persistence.*;
import javax.security.auth.Subject;
import javax.xml.bind.annotation.XmlRootElement;
import java.security.Principal;
import java.util.Objects;

/**
 * Created by Leonardo on 25/02/2015.
 */
@Entity
@Table(name = "CERES_USER",
        indexes = {@Index(name = "USER_USERNAME_INDEX", columnList = "USERNAME", unique = true)})
@ApiModel(value = "Usuário")
@JsonIgnoreProperties(value = "name")
@XmlRootElement(namespace = "ceres")
public class SystemUser extends AbstractEntity implements Principal, Comparable<SystemUser> {

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, name = "role")
    @Enumerated(value = EnumType.STRING)
    private UserCategory role;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JsonIgnore
    private Human human;

    protected SystemUser() {
    }

    public static SystemUser createUser(String username, String password, UserCategory role) {
        SystemUser systemUser = new SystemUser();
        systemUser.setUsername(username);
        systemUser.setRole(role);
        systemUser.setPassword(password);

        return systemUser;
    }

    public static SystemUser createUser(String username, String password) {
        SystemUser systemUser = new SystemUser();
        systemUser.setUsername(username);
        systemUser.setPassword(password);

        return systemUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Human getHuman() {
        return human;
    }

    public void setHuman(Human human) {
        this.human = human;
    }

    public UserCategory getRole() {
        return role;
    }

    public void setRole(UserCategory role) {
        this.role = role;
    }


    @Override
    public String getName() {
        return this.username;
    }

    @Override
    public boolean implies(Subject subject) {
        return subject != null && subject.getPrincipals().contains(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SystemUser that = (SystemUser) o;
        return Objects.equals(getUsername(), that.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername());
    }

    @Override
    public int compareTo(SystemUser o) {
        return this.username.compareTo(o.getUsername());
    }

    @Override
    public String toString() {
        return username;
    }


}
