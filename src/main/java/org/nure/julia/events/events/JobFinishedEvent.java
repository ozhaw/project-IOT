package org.nure.julia.events.events;

import org.springframework.context.ApplicationEvent;

public class JobFinishedEvent extends ApplicationEvent {
    public JobFinishedEvent(Object source) {
        super(source);
    }
}
