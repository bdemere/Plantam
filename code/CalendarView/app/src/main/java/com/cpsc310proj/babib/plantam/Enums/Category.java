package com.cpsc310proj.babib.plantam.Enums;

/**
 * Created by bemnet on 11/8/17.
 *
 * These are predefined constants for
 * the event categories that a user may
 * upload or subscribe to
 */

public enum Category {
    SPORT("sport"),
    CLUB("club"),
    ACADEMIC("academic"),
    SOCIAL("social");

    private String type_name;
    private Category(String type_name){
        this.type_name = type_name;
    }
    public String toString(){
        return type_name;
    }
}
