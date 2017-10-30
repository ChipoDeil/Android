package com.example.chipodeil.hookahfinder;

/**
 * Created by chipodeil on 30.10.2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Comment {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("updated_at")
    @Expose
    private String updated_at;
    @SerializedName("text")
    @Expose
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUpdated_at() {
        return updated_at;
    }
    public void seUpdated_at(int average_bill) {
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }



}