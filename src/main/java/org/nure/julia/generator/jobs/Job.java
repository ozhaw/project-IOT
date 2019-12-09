package org.nure.julia.generator.jobs;

import org.nure.julia.dto.JobLogDto;
import org.nure.julia.events.JobFinishedEvent;
import org.nure.julia.events.JobStatusChangedEvent;
import org.nure.julia.exceptions.WrongJobStateTransitionException;
import org.nure.julia.misc.JobPriority;
import org.nure.julia.misc.JobStatus;
import org.springframework.context.ApplicationEventPublisher;

import javax.validation.constraints.NotNull;
import java.util.*;

import static java.lang.String.format;

public abstract class Job<R> {
    private String id;
    private JobStatus jobStatus = JobStatus.NEW;
    private JobPriority priority = JobPriority.DEFAULT;
    private String deviceId;
    private String destination;

    private ApplicationEventPublisher eventPublisher;
    private List<JobLogDto> jobLogs;

    public Job(String deviceId, String destination) {
        this.deviceId = deviceId;
        this.id = UUID.randomUUID().toString();
        this.jobLogs = new ArrayList<>();
        this.destination = destination;
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

        this.eventPublisher.publishEvent(new JobStatusChangedEvent(this.id, this.id, this.jobStatus));
    }

    public ApplicationEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    public void setEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
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

    public String getDestination() {
        return destination;
    }

    public abstract R execute();

    public R run() {
        R result = this.execute();

        jobLogs.add(new JobLogDto(this.id, new Date(), true));
        this.eventPublisher.publishEvent(new JobFinishedEvent(result, this.destination));

        return result;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Job && id.equals(((Job) obj).id);
    }

}
