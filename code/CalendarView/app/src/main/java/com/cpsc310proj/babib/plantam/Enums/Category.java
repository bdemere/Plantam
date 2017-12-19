package com.cpsc310proj.babib.plantam.Enums;

/**
 * @author GROUP 4
 * @version 1.0
 *
 * These are predefined constants for
 * the event categories that a user may
 * upload or subscribe to
 */

public enum Category {
    SPORT("sport"),
    CLUB("club"),
    ACADEMIC("academic"),
    SOCIAL("social"),
    OTHER("other");

    private String type_name;
    Category(String type_name){
        this.type_name = type_name;
    }

    public static int getIndex(String category){
        for(Category c : values())
            if(c.equals(category)) return c.ordinal();

        return OTHER.ordinal();
    }
    public String toString(){
        return type_name;
    }
}
