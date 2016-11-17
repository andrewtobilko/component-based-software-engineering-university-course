package com.tobilko.event;

import javafx.event.EventType;

/**
 *
 * Created by Andrew Tobilko on 11/17/2016.
 *
 */
public class MyEventTypeProvider {

    private static EventType<MyEvent> type;

    public static EventType<MyEvent> getEventType() {
        if (type == null) {
            return type = new EventType<>("FILTER_PARAMETERS");
        }
        return type;
    }

}
