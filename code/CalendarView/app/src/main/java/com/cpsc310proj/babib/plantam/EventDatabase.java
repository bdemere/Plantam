package com.cpsc310proj.babib.plantam;

import com.cpsc310proj.babib.plantam.Event.CustomDate;
import com.cpsc310proj.babib.plantam.Event.Event;

import java.util.List;

/**
 * Created by bemnet on 12/14/17.
 */

public interface EventDatabase {
    void addEvent(Event event);
    void deleteEvent(Event event);
}
