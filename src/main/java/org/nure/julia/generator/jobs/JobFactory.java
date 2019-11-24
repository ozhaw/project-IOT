package org.nure.julia.generator.jobs;

import org.nure.julia.events.events.JobFinishedEvent;
import org.nure.julia.generator.jobs.spi.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${application.events.batch-result-auto-send}")
    private boolean autoBatchResultSendEnabled;

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
        pool.stream()
                .filter(batch -> id.equals(batch.getId()))
                .findFirst()
                .map(Batch::runJobsForResult)
                .filter(result -> autoBatchResultSendEnabled)
                .ifPresent(result -> this.publisher.publishEvent(new JobFinishedEvent(result)));
    }

    public void runAll() {
        pool.parallelStream()
                .map(Batch::runJobsForResult)
                .filter(result -> autoBatchResultSendEnabled)
                .forEach(result -> this.publisher.publishEvent(new JobFinishedEvent(result)));
    }

    private Batch getAvailableBatch() {
        return pool.stream()
                .filter(batch -> !batch.isFull())
                .findFirst()
                .orElseGet(() -> {
                    Batch batch = new Batch();
                    pool.add(batch);
                    return batch;
                });
    }

    public boolean isAutoBatchResultSendEnabled() {
        return autoBatchResultSendEnabled;
    }

}
