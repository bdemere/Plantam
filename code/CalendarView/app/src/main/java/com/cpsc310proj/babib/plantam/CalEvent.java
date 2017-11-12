package com.cpsc310proj.babib.plantam;

import java.util.*;

/**
 * Created by anhthuynguyen on 10/27/17.
 */

public class CalEvent {

    private String title;
    private String description;
    private String start_date;
    private String end_date;
    private EventCategory category;
    private Location location;


    //Constructors
    public CalEvent(String title, String description, String start, String end, EventCategory category, Location location) {
        this.title = title;
        this.description = description;
        this.start_date = start;
        this.end_date = end;
        this.category = category;
        this.location = location;
    }

    //Getters
    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getStartDate() {
        return start_date;
    }

    public String getEndDate() {
        return end_date;
    }

    public EventCategory getCategory() {
        return this.category;
    }

    public Location getLocation() {
        return location;
    }

    //Setters
    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;

    }

    public void setStartDate(String start_date) {
        this.start_date = start_date;
    }

    public void setEndDate(String end_date) {
        this.end_date = end_date;
    }

    public void setCategory(EventCategory category){
        this.category = category;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public CalEvent createEvent(String title, String description, String start, String end, EventCategory category, Location location) {
        return new CalEvent(title,description,start,end,category,location);
       // CalEvent(title,description,date,start_time,end_time);
    }

    public boolean publishEvent() {
        //wait until Server is done :>
        return true;
    }


    public void editEvent(CalEvent updatedEvent) {
        this.setTitle(updatedEvent.getTitle());
        this.setDescription(updatedEvent.getDescription());
        this.setStartDate(updatedEvent.getStartDate());
        this.setEndDate(updatedEvent.getEndDate());
    }

    public void deleteEvent(CalEvent event) {

        event = null;
    }

}
