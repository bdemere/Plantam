package com.cpsc310proj.babib.plantam.Event;

import java.io.Serializable;

/**
 * @author GROUP 4
 * @version 1.0
 * A class to store the key attribute of an event.
 */

public class EventInfo implements Serializable{
    public String eventUID = ""; //this events unique ID
    public String title = "";
    public String description = "";
    public String date = "";
    public String startTime = "";
    public String endTime = "";
    public String category = "";
    public String accessibility = "";
    public String location = "";

    public void setInfo(EventInfo info){
        eventUID = info.eventUID;
        title = info.title;
        description = info.description;
        date = info.date;
        startTime = info.startTime;
        endTime = info.endTime;
        category = info.category;
        accessibility = info.accessibility;
        location = info.location;
    }

    @Override
    public String toString() {
        return "EventInfo{" +
                "eventUID='" + eventUID + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", category='" + category + '\'' +
                ", location='" + location + '\'' +
                ", accessibility='" + accessibility + '\'' +
                '}';
    }


}
