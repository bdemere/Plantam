package com.cpsc310proj.babib.plantam.Enums;

/**
 * Created by GROUP 4 on 12/4/17.
 */

public enum Accessibility {
    PUBLIC("PUBLIC"),
    PRIVATE("PRIVATE"),
    USERPUBLIC("USERPUBLIC");

    private String name;

    Accessibility(String name){this.name = name;}

    @Override
    public String toString() {
        return name;
    }
}
