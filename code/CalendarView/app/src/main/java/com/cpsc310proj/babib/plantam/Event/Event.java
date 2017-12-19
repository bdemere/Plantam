package com.cpsc310proj.babib.plantam.Event;


import android.util.Log;
import com.cpsc310proj.babib.plantam.Firebase.User;
import com.cpsc310proj.babib.plantam.SQLiteDatabase.DatabaseEntry;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author GROUP 4
 * @version 1.0
 * An event class to define the properties of an event with some basic functionalities.
 */

public class Event implements DatabaseEntry, Serializable {
    //Event Table Columns names
    public static final String KEY_ID = "ID";
    public static final String KEY_TITLE = "TITLE";
    public static final String KEY_DATE = "DATE";
    public static final String KEY_START = "START_TIME";
    public static final String KEY_END = "END_TIME";
    public static final String KEY_DESCRIPTION = "DESCRIPTION";
    public static final String KEY_CATEGORY = "CATEGORY";
    public static final String KEY_ACCESSIBILITY = "ACCESSIBILITY";
    public static final String KEY_LOCATION = "LOCATION";


    public static String[] KEYS = {
            "",
            Event.KEY_ID,
            Event.KEY_TITLE,
            Event.KEY_DATE,
            Event.KEY_START,
            Event.KEY_END,
            Event.KEY_DESCRIPTION,
            Event.KEY_CATEGORY,
            Event.KEY_ACCESSIBILITY,
            Event.KEY_LOCATION
    };

    private User user = null;
    private EventInfo eventInfo;

    public Event(EventInfo info){
        eventInfo = new EventInfo();
        eventInfo.setInfo(info);
        //Log.d("EVENT: ", eventInfo.toString());
    }

    public Event(){
        eventInfo = new EventInfo();
        eventInfo.eventUID = UUID.randomUUID().toString();
    }

    public EventInfo getEventInfo(){
        return eventInfo;
    }
    //Getters
    public String getTitle() {
        return eventInfo.title;
    }

    public String getDescription() {
        return eventInfo.description;
    }

    public CustomDate getDate() {
        return new CustomDate(eventInfo.date);
    }

    public CustomTime getStartTime() {
        return new CustomTime(eventInfo.startTime);
    }

    public CustomTime getEndTime() {
        return new CustomTime(eventInfo.endTime);
    }

    public void setAccessibility(String accessibility){
        eventInfo.accessibility = accessibility;
    }

    public String getAccessibility(){
        return eventInfo.accessibility;
    }

    public User getUser() { return this.user;}



    //Setters
    public void setTitle(String title){
        eventInfo.title = title;
    }

    public void setUser(User user){this.user = user;}

    public void setDescription(String description){
        eventInfo.description = description;

    }

    public void setDate(String date){
        eventInfo.date = date;
    }

    public void setStartTime(String start_time){
        eventInfo.startTime = start_time;
    }

    public void setEndTime(String end_time){
        eventInfo.endTime = end_time;
    }
    public void setCategory(String category){
        eventInfo.category = category;
    }


    public void setEventUID(String uid){
        eventInfo.eventUID = uid;}

    public void setLocation(String location){
        eventInfo.location = location;
    }

    public String getLocation(){
        return eventInfo.location;
    }

    public String getEventUID(){
        Log.d("getEventUID(): ", eventInfo.eventUID.toString());
        return eventInfo.eventUID;
    }
    public String getCategory() {
        return eventInfo.category;
    }

    public boolean publishEvent() {
        //wait until Server is done :>
        return true;
    }

    public void deleteEvent(Event event) {
        event = null;
    }

    @Override
    public String toString() {
        return "Event{" +
                "event_uid='" + eventInfo.eventUID + '\'' +
                ", title='" + eventInfo.title + '\'' +
                ", description='" + eventInfo.description + '\'' +
                ", date='" + eventInfo.date + '\'' +
                ", start_time='" + eventInfo.startTime + '\'' +
                ", end_time='" + eventInfo.endTime + '\'' +
                ", category='" + eventInfo.category + '\'' +
                ", location='" + eventInfo.location + '\'' +
                ", accessibility='" + eventInfo.accessibility + '\'' +
                '}';
    }

    public String getTableSpecification(){
        return "";
    }
}
