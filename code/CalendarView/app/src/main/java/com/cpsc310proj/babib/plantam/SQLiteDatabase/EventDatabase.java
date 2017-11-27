package com.cpsc310proj.babib.plantam.SQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

import com.cpsc310proj.babib.plantam.Event;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anhthuynguyen on 11/2/17.
 */

public class EventDatabase {
    private static Context mContext;
    private static EventDatabase theEventDatabase;
    private static SQLiteDatabase mDatabase;

    /* Constructor */
    public EventDatabase(Context context) {
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
    public void addEvent(com.cpsc310proj.babib.plantam.Event event) {
        //get content value of an event
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_TITLE, event.getTitle().toString());
        values.put(DatabaseHelper.KEY_TITLE, event.getDate().toString());
        values.put(DatabaseHelper.KEY_START, event.getStartTime());
        values.put(DatabaseHelper.KEY_END, event.getEndTime());
        values.put(DatabaseHelper.KEY_DESCRIPTION, event.getDescription());
        values.put(DatabaseHelper.KEY_CATEGORY, event.getCategory());

        mDatabase.insert(DatabaseHelper.TABLE_NAME, null, values);
    }

    public void deleteEvent(com.cpsc310proj.babib.plantam.Event event) {
        String eventTitle = event.getTitle().toString();
        mDatabase.delete(DatabaseHelper.DATABASE_NAME, DatabaseHelper.KEY_TITLE + " = ?",
                new String[]{eventTitle});
    }

    public void updateEvent(com.cpsc310proj.babib.plantam.Event event){
        String eventTitle = event.getTitle().toString();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_TITLE, event.getTitle().toString());
        values.put(DatabaseHelper.KEY_START, event.getStartTime());
        values.put(DatabaseHelper.KEY_END, event.getEndTime());
        values.put(DatabaseHelper.KEY_DESCRIPTION, event.getDescription().toString());

        mDatabase.update(DatabaseHelper.DATABASE_NAME, values, DatabaseHelper.KEY_TITLE + " = ? ", new String[]{eventTitle});
    }

    public com.cpsc310proj.babib.plantam.Event getEvent(String eventTitle){
        Cursor dbCursor = mDatabase.query(
                DatabaseHelper.DATABASE_NAME,
                new String[]{
                        DatabaseHelper.KEY_TITLE,
                        DatabaseHelper.KEY_START,
                        DatabaseHelper.KEY_END,
                        DatabaseHelper.KEY_DESCRIPTION
                },
                eventTitle + "=?",
                new String[] {
                        eventTitle, null, null, null, null
                },
                null,
                null,
                "asc") ;

        if(dbCursor != null) { dbCursor.moveToFirst(); }

        Event event =
                new com.cpsc310proj.babib.plantam.Event(
                        dbCursor.getString(0),
                        dbCursor.getString(1),
                        dbCursor.getString(2),
                        dbCursor.getString(3),
                        dbCursor.getString(4),
                        dbCursor.getString(5));

        return event;
    }

    public ArrayList<Event> getEventAtDate(String date){
        ArrayList<Event> events = new ArrayList<>();
        Cursor dbCursor = mDatabase.query(DatabaseHelper.TABLE_NAME,
                null, null, null, null, null,
                DatabaseHelper.KEY_START + " ASC");//mDatabase.rawQuery(selectQuery, null);

        if(dbCursor.moveToFirst()){
            while(dbCursor.moveToNext()){
                if(dbCursor.getString(2).equals(date)) {
                    Event event =
                            new Event(
                                    //dbCursor.getString(0),
                                    dbCursor.getString(1),
                                    dbCursor.getString(2),
                                    dbCursor.getString(3),
                                    dbCursor.getString(4),
                                    dbCursor.getString(5),
                                    dbCursor.getString(6)
                            );

                    event.setEventUID(dbCursor.getColumnName(0));

                    events.add(event);
                }
            }
        }

        return events;
    }

    public ArrayList<Event> getAllEvents(){
        ArrayList<Event> eventList = new ArrayList<>();
        //String selectQuery = "SELECT *FROM" + DatabaseHelper.DATABASE_NAME;

        Cursor dbCursor = mDatabase.query(DatabaseHelper.TABLE_NAME,
                null, null, null, null, null,
                DatabaseHelper.KEY_START + " ASC");//mDatabase.rawQuery(selectQuery, null);

        if(dbCursor.moveToFirst()){
            while(dbCursor.moveToNext()){
                Event event =
                        new Event(
                                dbCursor.getString(0),
                                dbCursor.getString(1),
                                dbCursor.getString(2),
                                dbCursor.getString(3),
                                dbCursor.getString(4),
                                dbCursor.getString(5)
                        );
                eventList.add(event);
            }
        }

        return eventList;
    }
}
