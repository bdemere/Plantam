package com.cpsc310proj.babib.plantam.SQLiteDatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cpsc310proj.babib.plantam.Event.Event;

public class DatabaseHelper extends SQLiteOpenHelper { //change variables to public to try adding event to database
    // Database Version
    public static final int DATABASE_VERSION = 1;
    // Database Name
    public static final String DATABASE_NAME = "EVENT_DATABASE";
    // Contacts table name
    public static final String TABLE_NAME = DATABASE_NAME + "_events";


    public static String[] KEYS = Event.KEYS;




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
            "CREATE TABLE " +
            TABLE_NAME + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEYS[1] + " TEXT, " +
                    KEYS[2] + " TEXT, " +
                    KEYS[3] + " TEXT, " +
                    KEYS[4] + " TEXT, " +
                    KEYS[5] + " TEXT, " +
                    KEYS[6] + " TEXT, " +
                    KEYS[7] + " TEXT, " +
                    KEYS[8] + " TEXT  " +
            ");"
        );
    }


    /**
     * Given the name of a key, return its index in the table
     * @param keyName
     * @return an index of column in the TABLE name
     */
    private static int getKeyIndex(String keyName){
        for(int i = 0; i < KEYS.length; i++)
            if(KEYS[i].equalsIgnoreCase(keyName))
                return i;

        return -1;
    }

    /**
     * Given a database cursor, get the event
     * @param dbCursor
     * @return Event object
     */
    public static Event getEvent(Cursor dbCursor){
        Event event = new Event();
        event.setTitle(dbCursor.getString(
                getKeyIndex(Event.KEY_TITLE)));
        event.setDate(dbCursor.getString(
                getKeyIndex(Event.KEY_DATE)));
        event.setStartTime(dbCursor.getString(
                getKeyIndex(Event.KEY_START)));
        event.setEndTime(dbCursor.getString(
                getKeyIndex(Event.KEY_END)));
        event.setDescription(dbCursor.getString(
                getKeyIndex(Event.KEY_DESCRIPTION)));
        event.setCategory(dbCursor.getString(
                getKeyIndex(Event.KEY_CATEGORY)));
        event.setEventUID(dbCursor.getString(
                getKeyIndex(Event.KEY_ID)));
        event.setAccessibility(dbCursor.getString(
                getKeyIndex(Event.KEY_ACCESSIBILITY)));

        return event;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //Sort table based on starting time
        final String sortSQL = "select * from " + TABLE_NAME + " ORDER by " + Event.KEY_START
                + " asc";
        db.execSQL(sortSQL);

        // Creating tables again
        onCreate(db);
    }
}
