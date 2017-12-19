package com.cpsc310proj.babib.plantam;

import com.cpsc310proj.babib.plantam.Event.CustomDate;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by bemnet on 11/19/17.
 */

public class CurrentDate {
    private static Date mToday = new Date();
    private static Calendar mCalendar
            = Calendar.getInstance();

    public static long getDate(){
        mCalendar.setTime(mToday);
        return mCalendar.getTime().getTime();
    }

    public static int getTime(){
        mCalendar.setTime(mToday);
        return 1;
    }
    public static int getDay(){
        mCalendar.setTime(mToday);

        return mCalendar.get(Calendar.DATE);

    }

    public static int getMonth(){
        mCalendar.setTime(mToday);

        return mCalendar.get(Calendar.MONTH);

    }

    public static String getMonthAsString() {
        mCalendar.setTime(mToday);
        String month = "";
        switch (mCalendar.get(Calendar.MONTH)) {
            case (1): month = "Jan";
            case (2): month = "Feb";
            case (3): month = "Mar";
            case (4): month = "Apr";
            case (5): month = "May";
            case (6): month = "Jun";
            case (7): month = "Jul";
            case (8): month = "Aug";
            case (9): month = "Sep";
            case (10): month = "Oct";
            case (11): month = "Nov";
            case (12): month = "Dec";
        }
        return month;
    }
    public static int getYear(){
        mCalendar.setTime(mToday);

        return mCalendar.get(Calendar.YEAR);

    }

}
