package com.ceres.domain.embedabble;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by leonardo on 10/03/15.
 */
@Embeddable
@XmlRootElement(namespace = "ceres")
public class Name implements Serializable, Comparable<Name> {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String treatment;

    public Name() {
    }

    public Name(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Name(String treatment, String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.treatment = treatment;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    @JsonIgnore
    public Boolean isNullOrEmpty() {
        if (this.firstName == null || this.firstName.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(Name o) {

        if (this.getFirstName().compareTo(o.getFirstName()) == 0) {
            return this.getLastName().compareTo(o.getLastName());
        }

        return this.getFirstName().compareTo(o.getFirstName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Name)) return false;

        Name name = (Name) o;

        if (!firstName.equals(name.firstName)) return false;
        if (!lastName.equals(name.lastName)) return false;

        return true;
    }

    @Override
    public String toString() {
        return firstName;
    }
}
