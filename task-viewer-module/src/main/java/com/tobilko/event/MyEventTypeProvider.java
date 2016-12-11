package com.tobilko.event;

import javafx.event.EventType;

public class MyEventTypeProvider {

    private static EventType<MyEvent> type;

    public static EventType<MyEvent> getEventType() {
        return type == null ? type = new EventType<>("FILTER_PARAMETERS") : type;
    }

}
