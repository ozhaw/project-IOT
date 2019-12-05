package org.nure.julia.events.events;

import org.springframework.context.ApplicationEvent;

public class JobFinishedEvent extends ApplicationEvent {
    private String destination;

    public JobFinishedEvent(Object source, String destination) {
        super(source);

        this.destination = destination;
    }

    public JobFinishedEvent(Object source) {
        super(source);
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
