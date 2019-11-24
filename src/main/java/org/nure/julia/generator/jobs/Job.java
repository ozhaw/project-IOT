package org.nure.julia.generator.jobs;

import org.nure.julia.exceptions.WrongJobStateTransitionException;
import org.nure.julia.misc.JobPriority;
import org.nure.julia.misc.JobStatus;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.UUID;

import static java.lang.String.format;

public abstract class Job<R> {
    private String id;
    private JobStatus jobStatus = JobStatus.NEW;
    private JobPriority priority = JobPriority.DEFAULT;
    private String deviceId;

    public Job(String deviceId) {
        this.deviceId = deviceId;
        this.id = UUID.randomUUID().toString();
    }

    public JobStatus getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(@NotNull JobStatus jobStatus) {
        if (!this.jobStatus.isTransitionAllowed(jobStatus)) {
            throw new WrongJobStateTransitionException(format("Allowed transitions for %s are %s",
                    this.jobStatus.name(), Arrays.toString(this.jobStatus.getAllowedTransitions())));
        }

        this.jobStatus = jobStatus;
    }

    public JobPriority getPriority() {
        return priority;
    }

    public void setPriority(@NotNull JobPriority priority) {
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Job && id.equals(((Job) obj).id);
    }

    public abstract R execute();
}
