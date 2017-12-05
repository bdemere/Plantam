package com.cpsc310proj.babib.plantam.SQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.cpsc310proj.babib.plantam.Event.CustomDate;
import com.cpsc310proj.babib.plantam.Event.Event;

import java.util.ArrayList;

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
    public void addEvent(Event event) {
        //get content value of an event
        ContentValues values = new ContentValues();
        Log.d("Eve..base.addEvent(): ", event.getEventUID());
        values.put(Event.KEY_ID, event.getEventUID());
        values.put(Event.KEY_TITLE, event.getTitle());
        values.put(Event.KEY_DATE, event.getDate().toString());
        values.put(Event.KEY_START, event.getStartTime().toString());
        values.put(Event.KEY_END, event.getEndTime().toString());
        values.put(Event.KEY_DESCRIPTION, event.getDescription());
        values.put(Event.KEY_CATEGORY, event.getCategory());
        values.put(Event.KEY_ACCESSIBILITY, event.getAccessibility());

        mDatabase.insert(DatabaseHelper.TABLE_NAME, null, values);
    }

    public void deleteEvent(String eventID) {
        mDatabase.delete(DatabaseHelper.TABLE_NAME, Event.KEY_ID + " = ?",
                new String[]{eventID});
    }
//
//    public void updateEvent(Event event){
//        String eventTitle = event.getTitle().toString();
//        ContentValues values = new ContentValues();
//        values.put(Event.KEY_TITLE, event.getTitle().toString());
//        values.put(Event.KEY_START, event.getStartTime().toString());
//        values.put(Event.KEY_END, event.getEndTime().toString());
//        values.put(Event.KEY_DESCRIPTION, event.getDescription().toString());
//
//        mDatabase.update(DatabaseHelper.DATABASE_NAME, values, Event.KEY_TITLE + " = ? ", new String[]{eventTitle});
//    }
//
//    public Event getEvent(String eventTitle){
//        Cursor dbCursor = mDatabase.query(
//                DatabaseHelper.DATABASE_NAME,
//                new String[]{
//                        Event.KEY_TITLE,
//                        Event.KEY_START,
//                        Event.KEY_END,
//                        Event.KEY_DESCRIPTION
//                },
//                eventTitle + "=?",
//                new String[] {
//                        eventTitle, null, null, null, null
//                },
//                null,
//                null,
//                "asc") ;
//
//        if(dbCursor != null) { dbCursor.moveToFirst(); }
//
//        Event event =
//                new Event(
//                        dbCursor.getString(0),
//                        dbCursor.getString(1),
//                        dbCursor.getString(2),
//                        dbCursor.getString(3),
//                        dbCursor.getString(4),
//                        dbCursor.getString(5),
//                        dbCursor.getString(6));
//
//        return event;
//    }


    public Event getEvent(String UID){
        Cursor dbCursor = mDatabase.query(
                DatabaseHelper.TABLE_NAME,
                null, null, null, null, null,
                Event.KEY_START + " ASC"
        );

        if(dbCursor.moveToFirst()){
            do{
                Event event = DatabaseHelper.getEvent(dbCursor);
                Log.d("EventDatabase: ", event.toString());
                if(event.getEventUID().equals(UID))
                    return event;
            } while(dbCursor.moveToNext());
        }
        return null;
    }

    public ArrayList<Event> getEventsAtDate(CustomDate date){
        ArrayList<Event> events = new ArrayList<>();
        Cursor dbCursor = mDatabase.query(
                DatabaseHelper.TABLE_NAME,
                null, null, null, null, null,
                Event.KEY_START + " ASC"
        );

        //mDatabase.rawQuery(selectQuery, null);

        if(dbCursor.moveToFirst()){
            do{
                Event event = DatabaseHelper.getEvent(dbCursor);
                if(event.getDate().equals(date))
                    events.add(event);
            } while(dbCursor.moveToNext());
        }

        return events;
    }

    public ArrayList<Event> getAllEvents(){
        ArrayList<Event> eventList = new ArrayList<>();
        //String selectQuery = "SELECT *FROM" + DatabaseHelper.DATABASE_NAME;

        Cursor dbCursor = mDatabase.query(DatabaseHelper.TABLE_NAME,
                null, null, null, null, null,
                Event.KEY_START + " ASC");

        //mDatabase.rawQuery(selectQuery, null);

        if(dbCursor.moveToFirst()){
            do {
                eventList.add(DatabaseHelper.getEvent(dbCursor));
            } while(dbCursor.moveToNext());
        }

        return eventList;
    }
}
