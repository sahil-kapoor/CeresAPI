package com.ceres.domain.entity.human;

import com.ceres.domain.LocalDatePersistenceConverter;
import com.ceres.domain.embedabble.Contact;
import com.ceres.domain.embedabble.Name;
import com.ceres.domain.entity.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Created by leonardo on 10/03/15.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "DISCRIMINATOR")
@ApiModel(value = "Humano", subTypes = {Nutritionist.class, Patient.class}, description = "Representação genérica de uma pessoa")
@XmlRootElement(namespace = "ceres")
public abstract class Human extends AbstractEntity {

    @Embedded
    private Name name;

    @Embedded
    private Contact contact;

    @Column(nullable = false, name = "GENDER")
    @Enumerated(value = EnumType.ORDINAL)
    private Gender gender;

    @Column(name = "BIRTHDATE", nullable = true, columnDefinition = "DATE")
    @Convert(converter = LocalDatePersistenceConverter.class)
    @ApiModelProperty(value = "birthdate", example = "dd-mm-aaaa")
    private LocalDate birthdate;

    @OneToOne(mappedBy = "human", optional = true, targetEntity = SystemUser.class, fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private SystemUser user;

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public SystemUser getUser() {
        return user;
    }

    public void setUser(SystemUser user) {
        user.setHuman(this);
        this.user = user;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Human human = (Human) o;
        return Objects.equals(getName(), human.getName()) &&
                Objects.equals(getContact(), human.getContact()) &&
                Objects.equals(getGender(), human.getGender()) &&
                Objects.equals(getBirthdate(), human.getBirthdate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getContact(), getGender(), getBirthdate());
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return name.toString();
    }

}
