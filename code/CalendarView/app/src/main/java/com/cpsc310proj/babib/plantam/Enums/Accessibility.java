package com.cpsc310proj.babib.plantam.Enums;

/**
 * @author GROUP 4
 * @version 1.0
 * Enum to define finite possibilities for the privacy of an event
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
