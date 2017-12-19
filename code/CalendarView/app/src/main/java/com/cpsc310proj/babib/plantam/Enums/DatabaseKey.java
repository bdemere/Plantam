package com.cpsc310proj.babib.plantam.Enums;

/**
 * @author GROUP 4
 * @version 1.w
 * Enum to define finite possibilities for the potential keys in our database
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
