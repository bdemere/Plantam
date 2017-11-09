package com.cpsc310proj.babib.plantam;

//import java.time.MonthDay;

/**
 * Created by anhthuynguyen on 10/27/17.
 */

public class Event {

    private String title;
    private String description;
    private String date; //later change to enum or other data structure
    private float start_time;
    private float end_time;


    //Constructors
    public Event(String title, String description, String date, float start_time, float end_time) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    //Getters
    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDate() {
        return this.date;
    }

    public float getStartTime() {
        return this.start_time;
    }

    public float getEndTime() {
        return this.end_time;
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

    public void setStartTime(float start_time){
        this.start_time = start_time;
    }

    public void setEndTime(float end_time){
        this.end_time = end_time;
    }



    public Event createEvent(String title, String description, String date, float start_time, float end_time) {
        return new Event(title,description,date,start_time,end_time);
       // Event(title,description,date,start_time,end_time);
    }

    public boolean publishEvent() {

        //wait until Server is done :>
        return true;
    }


    public void editEvent(Event updatedEvent) {
        this.setTitle(updatedEvent.getTitle());
        this.setDescription(updatedEvent.getDescription());
        this.setDate(updatedEvent.getDate());
        this.setStartTime(updatedEvent.getStartTime());
        this.setEndTime(updatedEvent.getEndTime());
    }

    public void deleteEvent(Event event) {
        event = null;
    }

}
