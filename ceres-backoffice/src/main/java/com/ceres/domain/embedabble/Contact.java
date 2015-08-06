package com.ceres.domain.embedabble;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by leonardo on 11/03/15.
 */
@Embeddable
@XmlRootElement(namespace = "ceres")
public class Contact implements Serializable, Comparable<Contact> {

    @Column(nullable = true)
    private String homePhone;

    @Column(nullable = true)
    private String cellPhone;

    @Column(nullable = true)
    private String email;

    Contact() {
    }

    public Contact(String email) {
        this.email = email;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public Boolean isNullOrEmpty() {
        if (this.getEmail() == null || this.getEmail().isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;

        Contact contact = (Contact) o;

        if (!email.equals(contact.email)) return false;

        return true;
    }

    @Override
    public int compareTo(Contact o) {
        return this.email.compareTo(o.email);
    }

    @Override
    public String toString() {
        return email;
    }
}
