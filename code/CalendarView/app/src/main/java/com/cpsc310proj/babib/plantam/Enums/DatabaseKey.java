package com.cpsc310proj.babib.plantam.Enums;

/**
 * @author GROUP 4
<<<<<<< HEAD
 * @version 1
 * These are predefined constants for
 * the event categories that a user may
 * upload or subscribe to
=======
 * @version 1.w
 * Enum to define finite possibilities for the potential keys in our database
>>>>>>> f95354b47e6e7b5d98edd359b5c3a3540641a1c3
 */

public enum DatabaseKey {
    KEY_ID("ID"),
    KEY_TITLE("TITLE"),
    KEY_DATE("DATE"),
    KEY_START("START_TIME"),
    KEY_END("END_TIME"),
    KEY_DESCRIPTION("DESCRIPTION"),
    KEY_CATEGORY("CATEGORY");

    private String key_name;

    DatabaseKey(String type_name){
        this.key_name = type_name;
    }

    public String toString(){
        return key_name;
    }
}
