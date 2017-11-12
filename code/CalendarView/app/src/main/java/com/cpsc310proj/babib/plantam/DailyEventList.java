package com.cpsc310proj.babib.plantam;
import com.cpsc310proj.babib.plantam.Event;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by anhthuynguyen on 10/27/17.
 */

public class DailyEventList {

    private ArrayList<Event> list;

    //Constructor
    public DailyEventList() {
        ArrayList<Event> event_list = new ArrayList<Event>();
    }


    /*method to add a new event into correct DailyEventList and sort events every time
    * 2 ways: ---> 1.click on date and create event for the specific date
    *         2.create an event without specifying the date at first, use event handler to add
    *           event to the list
    */
    public void addEventToList(Event newEvent) {
        ;
    }

    public void sortEvent(Event newEvent) {
        for (int i = 0; i < list.size(); i++) {
            if (Integer.valueOf(newEvent.getStartTime()) > Integer.valueOf(list.get(i).getStartTime())) {
                list.add(i + 1, newEvent);
                break;
            }
        }
    }

    //Send information of all events to corresponding .xml file and let the file display
    public void displayAllEvent() {
        ;
    }


}
