package org.nure.julia.generator.jobs.spi;

import org.nure.julia.events.events.JobAddedEvent;
import org.nure.julia.exceptions.BatchIsFullException;
import org.nure.julia.exceptions.JobNotFoundException;
import org.nure.julia.generator.jobs.Job;
import org.nure.julia.misc.JobStatus;
import org.springframework.context.ApplicationEventPublisher;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

public class Batch {
    private static final Integer DEFAULT_POOL_SIZE = 16;

    private final int poolSize;

    private Set<Job> jobs = new HashSet<>();
    private String id = UUID.randomUUID().toString();
    private ApplicationEventPublisher eventPublisher;

    public Batch(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
        this.poolSize = DEFAULT_POOL_SIZE;
    }

    public Batch(int poolSize, ApplicationEventPublisher eventPublisher) {
        this.poolSize = poolSize;
        this.eventPublisher = eventPublisher;
    }

    public Batch addJob(@NotNull Job job) {
        if (isFull()) {
            throw new BatchIsFullException("Batch is full");
        } else {
            job.setEventPublisher(eventPublisher);
            job.setJobStatus(JobStatus.READY);
            jobs.add(job);
            this.eventPublisher.publishEvent(new JobAddedEvent(job.getId(), job.getId()));
        }

        return this;
    }

    public void runJobs() {
        this.prepareJobs();
        jobs.parallelStream().filter(job -> job.getJobStatus() == JobStatus.READY)
                .forEach(Job::run);
    }

    public List<Object> runJobsForResult() {
        this.prepareJobs();
        return jobs.parallelStream().filter(job -> job.getJobStatus() == JobStatus.READY)
                .map(Job::run)
                .collect(toList());
    }

    public void stopJob(@NotNull String jobId) {
        this.jobs.stream().filter(job -> job.getId().equals(jobId))
                .forEach(job -> job.setJobStatus(JobStatus.PAUSED));
    }

    public void resumeJob(@NotNull String jobId) {
        this.jobs.stream().filter(job -> job.getId().equals(jobId))
                .forEach(job -> job.setJobStatus(JobStatus.READY));
    }

    public JobStatus getJobStatus(@NotNull String jobId) {
        return this.jobs.stream().filter(job -> job.getId().equals(jobId))
                .map(Job::getJobStatus)
                .findFirst().orElseThrow(() -> new JobNotFoundException("Job not found"));
    }

    public boolean isFull() {
        return this.poolSize == jobs.size();
    }

    public int getSize() {
        return jobs.size();
    }

    public String getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Batch && id.equals(((Batch) obj).id);
    }

    private void prepareJobs() {
        jobs.parallelStream().filter(job -> job.getJobStatus() == JobStatus.NEW)
                .forEach(job -> job.setJobStatus(JobStatus.READY));
    }
}
