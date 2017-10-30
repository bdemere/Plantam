package com.cpsc310proj.babib.plantam;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by anhthuynguyen on 10/27/17.
 */

public class DailyEventList {

    private ArrayList<CalEvent> list;

    //Constructor
    public DailyEventList() {
        ArrayList<CalEvent> event_list = new ArrayList<CalEvent>();
    }


    /*method to add a new event into correct DailyEventList and sort events every time
    * 2 ways: 1.click on date and create event for the specific date
    *         2.create an event without specifying the date at first, use event handler to add
    *           event to the list
    */
    public void addEventToList(CalEvent newEvent) {
        ;
    }

    public void sortEvent(CalEvent newEvent) {
        for (int i = 0; i < list.size(); i++) {
            if (newEvent.getStartTime() > list.get(i).getStartTime()){
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
