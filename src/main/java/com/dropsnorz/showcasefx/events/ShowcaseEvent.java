package com.dropsnorz.showcasefx.events;

import javafx.event.Event;
import javafx.event.EventType;

public class ShowcaseEvent extends Event {

    private static final long serialVersionUID = 1L;

    /**
     * Construct a new JFXDialog {@code Event} with the specified event type
     *
     * @param eventType the event type
     */
    public ShowcaseEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    /**
     * This event occurs when a Showcase is stoped, no longer visible to the user
     * ( after the exit animation ends )
     */
    public static final EventType<ShowcaseEvent> STOPPED =
        new EventType<>(Event.ANY, "SHOWCASE_STOPPED");

    /**
     * This event occurs when a Showcase is started, visible to the user
     * ( after the entrance animation ends )
     */
    public static final EventType<ShowcaseEvent> STARTED =
        new EventType<>(Event.ANY, "SHOWCASE_STARTED");
    
    
    /**
     * This event occurs when a new Showcase Step is printed to the user
     * ( after the entrance animation ends )
     */
    public static final EventType<ShowcaseEvent> STEP_DISPLAY = 
        new EventType<>(Event.ANY, "SHOWCASE_STEP_DISPLAY");




}
