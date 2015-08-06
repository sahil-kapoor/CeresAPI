package com.ceres.controller.v1.views;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by leonardo on 27/06/15.
 */
@XmlRootElement(namespace = "ceres")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class UserView {

    public String username;
    public String password;

    public UserView() {
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
}
