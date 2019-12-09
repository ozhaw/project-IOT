package org.nure.julia.events;

import org.nure.julia.misc.JobStatus;
import org.springframework.context.ApplicationEvent;

public class JobStatusChangedEvent extends ApplicationEvent {
    private String jobId;
    private JobStatus jobStatus;

    public JobStatusChangedEvent(Object source, String jobId, JobStatus jobStatus) {
        super(source);
        this.jobId = jobId;
        this.jobStatus = jobStatus;
    }

    public JobStatusChangedEvent(Object source) {
        super(source);
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public JobStatus getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }
}
