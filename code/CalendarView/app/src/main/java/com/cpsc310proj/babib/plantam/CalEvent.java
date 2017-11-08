package com.cpsc310proj.babib.plantam;

import java.util.*;

/**
 * Created by anhthuynguyen on 10/27/17.
 */

public class CalEvent {

    private String title;
    private String description;
    private Date start_date;
    private Date end_date;
    private EventCategory category;


    //Constructors
    public CalEvent(String title, String description, Date start, Date end, EventCategory category) {
        this.title = title;
        this.description = description;
        this.start_date = start;
        this.end_date = end;
        this.category = category;
    }

    //Getters
    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public Date getStartDate() {
        return start_date;
    }

    public Date getEndDate() {
        return end_date;
    }

    public EventCategory getCategory() {
        return this.category;
    }
    //Setters
    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;

    }

    public void setStartDate(Date start_date) {
        this.start_date = start_date;
    }

    public void setEndDate(Date end_date) {
        this.end_date = end_date;
    }

    public void setCategory(EventCategory category){
        this.category = category;
    }

    public CalEvent createEvent(String title, String description, Date start, Date end, EventCategory category) {
        return new CalEvent(title,description,start,end,category);
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
