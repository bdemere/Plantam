package com.cpsc310proj.babib.plantam.Event;

//import java.time.MonthDay;

import android.util.Log;

import com.cpsc310proj.babib.plantam.Enums.Accessibility;
import com.cpsc310proj.babib.plantam.SQLiteDatabase.DatabaseEntry;

import java.util.Date;
import java.util.UUID;

/**
 * Created by anhthuynguyen on 10/27/17.
 */

public class Event implements DatabaseEntry {
    //Event Table Columns names
    public static final String KEY_ID = "ID";
    public static final String KEY_TITLE = "TITLE";
    public static final String KEY_DATE = "DATE";
    public static final String KEY_START = "START_TIME";
    public static final String KEY_END = "END_TIME";
    public static final String KEY_DESCRIPTION = "DESCRIPTION";
    public static final String KEY_CATEGORY = "CATEGORY";
    public static final String KEY_ACCESSIBILITY = "ACCESSIBILITY";

    public static String[] KEYS = {
            "",
            Event.KEY_ID,
            Event.KEY_TITLE,
            Event.KEY_DATE,
            Event.KEY_START,
            Event.KEY_END,
            Event.KEY_DESCRIPTION,
            Event.KEY_CATEGORY,
            Event.KEY_ACCESSIBILITY
    };


    private String event_uid; //this events unique ID
    private String title;
    private String description;
    private String date;
    private String start_time;
    private String end_time;
    private String category;
    private String accessibility;

    //Constructors
    public Event(
            String title,
            String date,
            String start_time,
            String end_time,
            String description,
            String category,
            String accessibility) {

        this.event_uid = UUID.randomUUID().toString() + ":" + title;
        this.title = title;
        this.description = description;
        this.date = date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.category = category.toString();
        this.accessibility = accessibility;
    }

    public Event(
            String title,
            CustomDate date,
            CustomTime start_time,
            CustomTime end_time,
            String description,
            String category,
            String accessibility) {

        this.event_uid = UUID.randomUUID().toString() + ":" + title;
        this.title = title;
        this.description = description;
        this.date = date.toString();
        this.start_time = start_time.toString();
        this.end_time = end_time.toString();
        this.category = category.toString();
        this.accessibility = accessibility;
    }

    public Event(){
        event_uid = UUID.randomUUID().toString();
    }


    //Getters
    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public CustomDate getDate() {
        return new CustomDate(this.date);
    }

    public CustomTime getStartTime() {
        return new CustomTime(this.start_time);
    }

    public CustomTime getEndTime() {
        return new CustomTime(this.end_time);
    }

    public void setAccessibility(String accessibility){
        this.accessibility = accessibility;
    }

    public String getAccessibility(){
        return accessibility;
    }



    //Setters
    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;

    }

    public void setDate(String date){
        this.date = date;
    }

    public void setStartTime(String start_time){
        this.start_time = start_time;
    }

    public void setEndTime(String end_time){
        this.end_time = end_time;
    }
    public void setCategory(String category){
        this.category = category;
    }


    public void setEventUID(String uid){event_uid = uid;}

    public String getEventUID(){
        Log.d("getEventUID(): ", event_uid.toString());
        return event_uid;
    }
    public String getCategory() {
        return category;
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
                "event_uid='" + event_uid + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", category='" + category + '\'' +
                ", accessibility='" + accessibility + '\'' +
                '}';
    }

    public String getTableSpecification(){
        return "";
    }
}
