package org.nure.julia.generator.spi;

import org.nure.julia.generator.jobs.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobRunner {

    private final JobFactory jobFactory;

    @Autowired
    public JobRunner(JobFactory jobFactory) {
        this.jobFactory = jobFactory;
    }

    @Scheduled(fixedRate = 30000)
    public void run() {
        jobFactory.runAll();
    }

    public void run(String jobId) {
        jobFactory.run(jobId);
    }

}
