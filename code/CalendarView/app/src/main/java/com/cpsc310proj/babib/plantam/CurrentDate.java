package com.cpsc310proj.babib.plantam;

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
    public static int getYear(){
        mCalendar.setTime(mToday);

        return mCalendar.get(Calendar.YEAR);

    }
}
