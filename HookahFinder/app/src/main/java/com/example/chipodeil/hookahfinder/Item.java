package com.example.chipodeil.hookahfinder;

import java.io.Serializable;

/**
 * Created by chipodeil on 29.10.2017.
 */

public class Item implements Serializable{
    String title;
    int averagePrice;
    String desc;
    boolean opened;
    float rating;

    Item(String title, int averagePrice, String desc, boolean opened, float rating){
        this.title = title;
        this. averagePrice = averagePrice;
        this.desc = desc;
        this.opened = opened;
        this.rating = rating;
    }

}
