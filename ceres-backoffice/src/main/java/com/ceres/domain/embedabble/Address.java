package com.ceres.domain.embedabble;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by leonardo on 10/03/15.
 */
@Embeddable
@XmlRootElement(namespace = "ceres")
public class Address implements Serializable {

    @Column(nullable = true)
    private String street;

    @Column(nullable = true)
    private String number;

    @Column(nullable = true)
    private String neighborhood;

    @Column(nullable = true)
    private String city;

    @Column(nullable = true)
    private String state;

    @Column(nullable = true)
    private String zipCode;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        Address address = (Address) o;

        if (city != null ? !city.equals(address.city) : address.city != null) return false;
        if (neighborhood != null ? !neighborhood.equals(address.neighborhood) : address.neighborhood != null)
            return false;
        if (number != null ? !number.equals(address.number) : address.number != null) return false;
        if (state != null ? !state.equals(address.state) : address.state != null) return false;
        if (street != null ? !street.equals(address.street) : address.street != null) return false;
        if (zipCode != null ? !zipCode.equals(address.zipCode) : address.zipCode != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", neighborhood='" + neighborhood + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
