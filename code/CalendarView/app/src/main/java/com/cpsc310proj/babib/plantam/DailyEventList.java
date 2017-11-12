package com.cpsc310proj.babib.plantam;
import java.util.ArrayList;

/**
 * Created by anhthuynguyen on 10/27/17.
 */

public class DailyEventList {

    public static ArrayList<Event> list = null;

//    //Constructor
//    public DailyEventList() {
//        ArrayList<Event> event_list = new ArrayList<Event>();
//    }


    public static ArrayList<Event> getEventListInstance(){
        if(list == null){
            list = new ArrayList<>();
        }
        return list;
    }
    /*method to add a new event into correct DailyEventList and sort events every time
    * 2 ways: 1.click on date and create event for the specific date
    *         2.create an event without specifying the date at first, use event handler to add
    *           event to the list
    */
    public static void addEventToList(Event newEvent) {
        if(list == null)
            getEventListInstance().add(newEvent);
        else
            list.add(newEvent);
    }

//    public void sortEvent(Event newEvent) {
//        for (int i = 0; i < list.size(); i++) {
//            if (newEvent.getStartTime() > list.get(i).getStartTime()){
//                list.add(i + 1, newEvent);
//                break;
//            }
//        }
//    }

    //Send information of all events to corresponding .xml file and let the file display
    public void displayAllEvent() {
        ;
    }


}
