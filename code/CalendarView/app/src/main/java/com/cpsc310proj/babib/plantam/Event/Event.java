package com.cpsc310proj.babib.plantam.Event;

//import java.time.MonthDay;

import android.util.Log;

import com.cpsc310proj.babib.plantam.Enums.Accessibility;
import com.cpsc310proj.babib.plantam.SQLiteDatabase.DatabaseEntry;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by anhthuynguyen on 10/27/17.
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


//    private String event_uid; //this events unique ID
//    private String title;
//    private String description;
//    private String date;
//    private String start_time;
//    private String end_time;
//    private String category;
//    private String accessibility;
    private EventInfo mEventInfo;

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
        mEventInfo = new EventInfo();
        mEventInfo.setInfo(info);
        //Log.d("EVENT: ", mEventInfo.toString());
    }


    public Event(){
        mEventInfo = new EventInfo();
        mEventInfo.mEventUID = UUID.randomUUID().toString();
    }


    public EventInfo getEventInfo(){
        return mEventInfo;
    }
    //Getters
    public String getTitle() {
        return mEventInfo.mTitle;
    }

    public String getDescription() {
        return mEventInfo.mDescription;
    }

    public CustomDate getDate() {
        return new CustomDate(mEventInfo.mDate);
    }

    public CustomTime getStartTime() {
        return new CustomTime(mEventInfo.mStartTime);
    }

    public CustomTime getEndTime() {
        return new CustomTime(mEventInfo.mEndTime);
    }

    public void setAccessibility(String accessibility){
        mEventInfo.mAccessibility = accessibility;
    }

    public String getAccessibility(){
        return mEventInfo.mAccessibility;
    }



    //Setters
    public void setTitle(String title){
        mEventInfo.mTitle = title;
    }

    public void setDescription(String description){
        mEventInfo.mDescription = description;

    }

    public void setDate(String date){
        mEventInfo.mDate = date;
    }

    public void setStartTime(String start_time){
        mEventInfo.mStartTime = start_time;
    }

    public void setEndTime(String end_time){
        mEventInfo.mEndTime = end_time;
    }
    public void setCategory(String category){
        mEventInfo.mCategory = category;
    }


    public void setEventUID(String uid){mEventInfo.mEventUID = uid;}

    public String getEventUID(){
        Log.d("getEventUID(): ", mEventInfo.mEventUID.toString());
        return mEventInfo.mEventUID;
    }
    public String getCategory() {
        return mEventInfo.mCategory;
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
                "event_uid='" + mEventInfo.mEventUID + '\'' +
                ", title='" + mEventInfo.mTitle + '\'' +
                ", description='" + mEventInfo.mDescription + '\'' +
                ", date='" + mEventInfo.mDate + '\'' +
                ", start_time='" + mEventInfo.mStartTime + '\'' +
                ", end_time='" + mEventInfo.mEndTime + '\'' +
                ", category='" + mEventInfo.mCategory + '\'' +
                ", accessibility='" + mEventInfo.mAccessibility + '\'' +
                '}';
    }

    public String getTableSpecification(){
        return "";
    }
}
