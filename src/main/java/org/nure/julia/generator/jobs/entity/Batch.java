package org.nure.julia.generator.jobs.entity;

import org.nure.julia.exceptions.BatchIsFullException;
import org.nure.julia.misc.JobStatus;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Batch {
    private static final Integer DEFAULT_POOL_SIZE = 16;

    private final int poolSize;

    private Set<Job> jobs = new TreeSet<>();
    private String id = UUID.randomUUID().toString();

    public Batch() {
        this.poolSize = DEFAULT_POOL_SIZE;
    }

    public Batch(int poolSize) {
        this.poolSize = poolSize;
    }

    public Batch addJob(@NotNull Job job) {
        if (isFull()) {
            throw new BatchIsFullException("Batch is full");
        } else {
            job.setJobStatus(JobStatus.READY);
            jobs.add(job);
        }

        return this;
    }

    public void runJobs() {
        List<Object> jobResult = jobs.stream()
                .map(Job::execute)
                .collect(toList());
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
}
