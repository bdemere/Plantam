package com.cpsc310proj.babib.plantam.Event;

import android.util.Log;

/**
 * Created by bemnet on 12/3/17.
 */

public class CustomDate {
    private int year;
    private int month;
    private int day;

    public CustomDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public CustomDate(){};

    public CustomDate(String date){
        Log.d("CustomDate: ", date);
        String[] parts = date.split("/");
        day = Integer.parseInt(parts[0]);
        month = Integer.parseInt(parts[1]);
        year = Integer.parseInt(parts[2]);

    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public CustomDate setYear(int year) {
        this.year = year;
        return this;
    }

    public CustomDate setMonth(int month) {
        this.month = month;
        return this;
    }

    public CustomDate setDay(int day) {
        this.day = day;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomDate that = (CustomDate) o;

        if (year != that.year) return false;
        if (month != that.month) return false;
        return day == that.day;
    }

    @Override
    public String toString() {
        return day+"/"+month+"/"+year;
    }

}
