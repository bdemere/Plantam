package com.cpsc310proj.babib.plantam.Event;

//import java.time.MonthDay;

import android.util.Log;

import com.cpsc310proj.babib.plantam.Firebase.User;
import com.cpsc310proj.babib.plantam.SQLiteDatabase.DatabaseEntry;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author GROUP 4
 * @version 1.0
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


//    private String event_uid; //this events unique ID
//    private String title;
//    private String description;
//    private String date;
//    private String start_time;
//    private String end_time;
//    private String category;
//    private String accessibility;
    private User user = null;
    private EventInfo eventInfo;

//    //Constructors
//    public Event(
//            String title,
//            String date,
//            String start_time,
//            String end_time,
//            String description,
//            String category,
//            String accessibility) {
//
//        this.event_uid = UUID.randomUUID().toString() + ":" + title;
//        this.title = title;
//        this.description = description;
//        this.date = date;
//        this.start_time = start_time;
//        this.end_time = end_time;
//        this.category = category.toString();
//        this.accessibility = accessibility;
//    }
//
//    public Event(
//            String title,
//            CustomDate date,
//            CustomTime start_time,
//            CustomTime end_time,
//            String description,
//            String category,
//            String accessibility) {
//
//        this.event_uid = UUID.randomUUID().toString() + ":" + title;
//        this.title = title;
//        this.description = description;
//        this.date = date.toString();
//        this.start_time = start_time.toString();
//        this.end_time = end_time.toString();
//        this.category = category.toString();
//        this.accessibility = accessibility;
//    }

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

//
//    public Event createEvent(String title, String description, String date, float start_time, float end_time) {
//        return new Event(title,description,date,start_time,end_time);
//       // Event(title,description,date,start_time,end_time);
//    }

    public boolean publishEvent() {

        //wait until Server is done :>
        return true;
    }


//    public void editEvent(Event updatedEvent) {
//        this.setTitle(updatedEvent.getTitle());
//        this.setDescription(updatedEvent.getDescription());
//        this.setDate(updatedEvent.getDate());
//        this.setStartTime(updatedEvent.getStartTime());
//        this.setEndTime(updatedEvent.getEndTime());
//    }

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
