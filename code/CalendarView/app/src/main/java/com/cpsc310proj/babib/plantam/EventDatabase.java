package com.cpsc310proj.babib.plantam;

import com.cpsc310proj.babib.plantam.Event.Event;

/**
 * @author GROUP 4
 * @version 1.0
 */

public interface EventDatabase {
    void addEvent(Event event);
    void deleteEvent(Event event);
}
