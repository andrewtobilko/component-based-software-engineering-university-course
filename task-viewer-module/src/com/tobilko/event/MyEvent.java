package com.tobilko.event;

import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

/**
 *
 * Created by Andrew Tobilko on 11/17/2016.
 *
 */
public class MyEvent extends Event {

    public MyEvent(@NamedArg("eventType") EventType<? extends Event> eventType) {
        super(eventType);
    }

    public MyEvent(@NamedArg("source") Object source, @NamedArg("target") EventTarget target, @NamedArg("eventType") EventType<? extends Event> eventType) {
        super(source, target, eventType);
    }


}
