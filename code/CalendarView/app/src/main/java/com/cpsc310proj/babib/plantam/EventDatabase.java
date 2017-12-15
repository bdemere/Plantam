package com.cpsc310proj.babib.plantam;

import com.cpsc310proj.babib.plantam.Event.CustomDate;
import com.cpsc310proj.babib.plantam.Event.Event;

import java.util.List;

/**
 * Created by bemnet on 12/14/17.
 */

public interface EventDatabase {
    public void addEvent(Event event);
    public void deleteEvent(Event event);
}
