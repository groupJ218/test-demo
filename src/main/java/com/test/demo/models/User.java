package com.test.demo.models;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {

    public static final String CLASS_NAME = "user";

    private String className;
    private String idUser;
    private String name;
    private String phone;
    private String email;
    private String status;
    private String country;
    private String login;
    private String pass;

    public User() {
        this.idUser = String.valueOf(UUID.randomUUID());
        this.className = CLASS_NAME;
    }

    public User(String idUser, String name, String phone, String email, String status, String country, String login, String pass) {
        super();
        this.className = CLASS_NAME;
        this.idUser = idUser;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.country = country;
        this.login = login;
        this.pass = pass;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getClassName() {
        return className;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                ", idUser='" + idUser + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                ", country='" + country + '\'' +
                ", login='" + login + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}

