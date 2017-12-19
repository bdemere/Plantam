package com.cpsc310proj.babib.plantam.Layouts;

import android.content.Context;
import android.os.AsyncTask;
import com.cpsc310proj.babib.plantam.CurrentDate;
import com.cpsc310proj.babib.plantam.Event.Event;
import com.cpsc310proj.babib.plantam.Layouts.CalendarLayout.CalendarActivity;
import com.cpsc310proj.babib.plantam.SQLiteDatabase.SQLiteEventDatabase;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.jsoup.Jsoup;

/**
 * @author GROUP 4
 * @version 1.0
 * This class is used for HTML parsing from URL using Jsoup.
 */
public class FetchData extends AsyncTask<Void, Void, Void> {

    private final Context mContext;

    CalendarActivity activity;
    SQLiteEventDatabase db;
    //public EventForm mForm;

    List<String> event_result = new LinkedList<String>();

    List<String> event_name = new LinkedList<String>();
    List<String> event_time = new LinkedList<String>();
    List<String> event_location = new LinkedList<String>();

    public FetchData(Context context) {
        super();
        this.mContext = context;
        this.db = new SQLiteEventDatabase(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        org.jsoup.nodes.Document document;
        org.jsoup.select.Elements event_collections, time_collections, location_collections;
        try {
            //Get Document object after parsing the html from given url.
            document = Jsoup.connect("http://www.trincoll.edu/TrinityToday/Pages/default.aspx").get();
            time_collections = document.select(".TCTT_date-time");
            location_collections = document.select(".TCTT_location");
            event_collections = document.select(".TCTT_bottom-event");

            for (int i=0; i < event_collections.size(); i++) {
                if (event_collections.get(i).text().contains("Dec 19")) //change to Current Date + 1
                    break;
                else {
                    String time = time_collections.get(i).text();
                    event_time.add(time.substring(0,time.length()-3));
                    String event = event_collections.get(i).text();
                    event = event.substring(0, event.indexOf(time));
                    event_name.add(event);
                    event_location.add(location_collections.get(i).text());

                    event_result.add(event);
                    event_result.add(time);
                    event_result.add(location_collections.get(i).text());
                }
            }
            System.out.println(event_name.get(0));
            //event_name.set(0,event_name.get(0).substring(7));
            System.out.println(event_name.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Event new_event = new Event();

        for (int i = 0; i < event_name.size(); i++) {
            new_event.setTitle(event_name.get(i));
            new_event.setDescription("Trinity Today Event");
            new_event.setDate(CurrentDate.getDay() + "/" + CurrentDate.getMonth() + "/" + CurrentDate.getYear());
            new_event.setStartTime(event_time.get(i));
            new_event.setEndTime(event_time.get(i));
            new_event.setLocation(event_location.get(i));
            new_event.setAccessibility("PUBLIC");

            validity_check(new_event);
            db.addEvent(new_event);
        }


    return null;

    }

    private void validity_check(Event event) throws IllegalArgumentException{

        /** Check if each field and dialog is filled **/

        if(event.getTitle().isEmpty())
            throw new IllegalArgumentException("Enter title");
        if(event.getDate().toString().isEmpty())
            throw new IllegalArgumentException("Pick date");
        if(event.getStartTime().toString().isEmpty())
            throw new IllegalArgumentException("Pick start time");
        if(event.getEndTime().toString().isEmpty())
            throw new IllegalArgumentException("Pick end time");
        if(event.getLocation().isEmpty())
            throw new IllegalArgumentException("Pick location");

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        activity.event_name = this.event_name;
        activity.event_time = this.event_time;
        activity.event_location = this.event_location;
        System.out.println("HERE IN FETCH DATA!!!!!!!!!!!!!!" + activity.event_name.size());

    }


}
