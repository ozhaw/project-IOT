package org.nure.julia.events;

import org.springframework.context.ApplicationEvent;

public class JobAddedEvent extends ApplicationEvent {
    private String jobId;

    public JobAddedEvent(Object source, String jobId) {
        super(source);
        this.jobId = jobId;
    }

    public JobAddedEvent(Object source) {
        super(source);
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }
}
