package com.cpsc310proj.babib.plantam.Event;

import java.util.List;

/**
 * Created by bemnet on 12/3/17.
 */

public class CustomTime {
    private int hour;
    private int min;

    public CustomTime(int hour, int min) {
        this.hour = hour;
        this.min = min;
    }

    public CustomTime(){};

    public CustomTime(String time){
        String[] parts = time.split(":");
        hour = Integer.parseInt(parts[0]);
        min = Integer.parseInt(parts[1]);
    }

    public int getHour() {
        return hour;
    }

    public int getMin() {
        return min;
    }

    public CustomTime setHour(int hour) {
        this.hour = hour;
        return this;
    }

    public CustomTime setMin(int min) {
        this.min = min;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomTime that = (CustomTime) o;

        if (hour != that.hour) return false;
        return min == that.min;
    }

    @Override
    public String toString() {
        return hour + ":" + min;
    }
}
