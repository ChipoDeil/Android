package com.example.chipodeil.authproject;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by chipodeil on 27.11.2017.
 */

public class Note extends RealmObject implements Serializable {

    String text;
    String login;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
