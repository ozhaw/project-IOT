package org.nure.julia.generator.jobs;

import org.nure.julia.generator.jobs.entity.Batch;
import org.nure.julia.generator.jobs.entity.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Component
public class JobFactory {
    private final List<Function<String, Job>> availableJobTypes;

    private final Set<Batch> pool = new HashSet<>();

    @Autowired
    public JobFactory(List<Function<String, Job>> availableJobTypes) {
        this.availableJobTypes = availableJobTypes;
    }

    public String registerDevice(String deviceId) {
        final Batch batch = getAvailableBatch();
        availableJobTypes.stream()
                .map(constructor -> constructor.apply(deviceId))
                .forEach(batch::addJob);

        return batch.getId();
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

}
