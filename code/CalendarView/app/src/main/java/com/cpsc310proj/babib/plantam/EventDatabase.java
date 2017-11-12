package com.cpsc310proj.babib.plantam;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by anhthuynguyen on 11/2/17.
 */

public class EventDatabase {
    private static Context mContext;
    private static EventDatabase theEventDatabase;
    private static SQLiteDatabase mDatabase;

    /* Constructor */
    private EventDatabase(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new DatabaseHelper(mContext).getWritableDatabase();
    }

    /* Retrieve an instance from event database */
    public static EventDatabase getEventDatabase(Context context) {
        if (theEventDatabase == null) {
            theEventDatabase = new EventDatabase(context);
        }
        return theEventDatabase;
    }

    /* Do we need a addCommentText()? */

    /* Add a new event to the database */
    public void addEvent(Event event) {
        //get content value of an event
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_TITLE, event.getTitle().toString());
        values.put(DatabaseHelper.KEY_TITLE, event.getDate().toString());
        values.put(DatabaseHelper.KEY_START, event.getStartTime());
        values.put(DatabaseHelper.KEY_END, event.getEndTime());
        values.put(DatabaseHelper.KEY_DESCRP, event.getDescription().toString());

        mDatabase.insert(DatabaseHelper.DATABASE_NAME, null, values);
    }

    public void deleteEvent(Event event) {
        String eventTitle = event.getTitle().toString();
        mDatabase.delete(DatabaseHelper.DATABASE_NAME, DatabaseHelper.KEY_TITLE + " = ?",
                new String[]{eventTitle});
    }

    public void updateEvent(Event event){
        String eventTitle = event.getTitle().toString();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_TITLE, event.getTitle().toString());
        values.put(DatabaseHelper.KEY_START, event.getStartTime());
        values.put(DatabaseHelper.KEY_END, event.getEndTime());
        values.put(DatabaseHelper.KEY_DESCRP, event.getDescription().toString());

        mDatabase.update(DatabaseHelper.DATABASE_NAME, values, DatabaseHelper.KEY_TITLE + " = ? ", new String[]{eventTitle});
    }
}
