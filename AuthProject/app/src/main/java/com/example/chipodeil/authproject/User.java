package com.example.chipodeil.authproject;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by chipodeil on 26.11.2017.
 */

public class User extends RealmObject implements Serializable{

    private String login;
    private String password;
    private String pin;
    private boolean isActive;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
