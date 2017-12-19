package com.cpsc310proj.babib.plantam.Enums;

/**
 * Created by GROUP 4 on 11/8/17.
 *
 * These are predefined constants for
 * the event categories that a user may
 * upload or subscribe to
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
