package org.nure.julia.generator.jobs;

import org.nure.julia.exceptions.JobNotFoundException;
import org.nure.julia.generator.jobs.spi.Batch;
import org.nure.julia.misc.JobStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Component
public class JobFactory {
    private final List<Function<String, Job>> availableJobTypes;
    private final ApplicationEventPublisher publisher;

    private final Set<Batch> pool = new HashSet<>();

    @Autowired
    public JobFactory(List<Function<String, Job>> availableJobTypes, ApplicationEventPublisher publisher) {
        this.availableJobTypes = availableJobTypes;
        this.publisher = publisher;
    }

    public String registerDevice(@NotNull String deviceId) {
        final Batch batch = getAvailableBatch();
        availableJobTypes.stream()
                .map(constructor -> constructor.apply(deviceId))
                .forEach(batch::addJob);

        return batch.getId();
    }

    public void run(@NotNull final String id) {
        pool.stream().filter(batch -> id.equals(batch.getId()))
                .findFirst()
                .ifPresent(Batch::runJobsForResult);
    }

    public void runAll() {
        pool.forEach(Batch::runJobsForResult);
    }

    public void stopJob(@NotNull String batchId, @NotNull String jobId) {
        pool.stream().filter(batch -> batch.getId().equals(batchId))
                .forEach(batch -> batch.stopJob(jobId));
    }

    public void resumeJob(@NotNull String batchId, @NotNull String jobId) {
        pool.stream().filter(batch -> batch.getId().equals(batchId))
                .forEach(batch -> batch.resumeJob(jobId));
    }

    public JobStatus getJobStatus(@NotNull String batchId, @NotNull String jobId) {
        return pool.stream().filter(batch -> batch.getId().equals(batchId))
                .map(batch -> batch.getJobStatus(jobId))
                .findFirst().orElseThrow(() -> new JobNotFoundException("Job not found"));
    }

    private Batch getAvailableBatch() {
        return pool.stream()
                .filter(batch -> !batch.isFull())
                .findFirst()
                .orElseGet(() -> {
                    Batch batch = new Batch(publisher);
                    pool.add(batch);
                    return batch;
                });
    }

}
