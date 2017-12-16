package com.cpsc310proj.babib.plantam.SQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.util.Log;

import com.cpsc310proj.babib.plantam.DataObserver;
import com.cpsc310proj.babib.plantam.Event.CustomDate;
import com.cpsc310proj.babib.plantam.Event.Event;
import com.cpsc310proj.babib.plantam.EventDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anhthuynguyen on 11/2/17.
 */

public class SQLiteEventDatabase implements EventDatabase, Serializable{
    private static Context mContext;
    private static SQLiteEventDatabase theSQLiteEventDatabase;
    private static SQLiteDatabase mDatabase;

    /* Constructor */
    public SQLiteEventDatabase(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new DatabaseHelper(mContext).getWritableDatabase();
    }

    /* Retrieve an instance from event database */
    public static SQLiteEventDatabase getEventDatabase(Context context) {
        if (theSQLiteEventDatabase == null) {
            theSQLiteEventDatabase = new SQLiteEventDatabase(context);
        }
        theSQLiteEventDatabase.setContext(context);
        return theSQLiteEventDatabase;
    }


    /**
     * This is a list of Observers that wait on data change on the local cache
     *
     **/
    private static ArrayList<DataObserver>
            dataObservers = null;


    /**
     * This method is used to add observer objects that
     * will be notified when there is a change made
     * to the downloaded data
     * @param observer
     */
    public static void addObserver(DataObserver observer){
        if(dataObservers == null) { //if not initialized
            dataObservers = new ArrayList<>();
            dataObservers.add(observer);
        } else {
            dataObservers.add(observer);
        }
    }

    /**
     * Notify all observers that a data has changed
     */
    public static void eventDataUpdated(){
        if(dataObservers != null)
            for(DataObserver dataObserver : dataObservers)
                dataObserver.eventDataChanged();
    }

    /**
     * Remove an observer
     * @param observer
     * @return
     */
    public static boolean removeObserver(DataObserver observer){
        return dataObservers.remove(observer);
    }


    public void setContext(Context context){
        mContext = context;
    }
    /* Do we need a addCommentText() */

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
        values.put(Event.KEY_LOCATION, event.getLocation());

        eventDataUpdated();
        mDatabase.insert(DatabaseHelper.TABLE_NAME, null, values);
    }

    public void deleteEvent(String eventID) {
        mDatabase.delete(DatabaseHelper.TABLE_NAME, Event.KEY_ID + " = ?",
                new String[]{eventID});
    }

    public void deleteEvent(Event event){
        eventDataUpdated();
        mDatabase.delete(DatabaseHelper.TABLE_NAME, Event.KEY_ID + " = ?",
                new String[]{event.getEventUID()});
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
//        mDatabase.updateEventsData(DatabaseHelper.DATABASE_NAME, values, Event.KEY_TITLE + " = ? ", new String[]{eventTitle});
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
                Log.d("SQLiteEventDatabase: ", event.toString());
                if(event.getEventUID().equals(UID))
                    return event;
            } while(dbCursor.moveToNext());
        }
        return null;
    }

    public List<Event> getEventsAtDate(CustomDate date){
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
