package com.example.chipodeil.hookahfinder;

/**
 * Created by chipodeil on 30.10.2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CoffeeShops implements Serializable {

    @SerializedName("updated_at")
    @Expose
    private String updated_at;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("average_bill")
    @Expose
    private int average_bill;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getAverage_bill() {
        return average_bill;
    }
    public void setAverage_bill(int average_bill) {
        this.average_bill = average_bill;
    }

    public String getUpdated_at() {
        return updated_at;
    }
    public void seUpdated_at(int average_bill) {
        this.updated_at = updated_at;
    }

    public String getDescription() {
        return description;
    }
    public void seDescription(int average_bill) {
        this.description = description;
    }



}
