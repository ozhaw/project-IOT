package org.nure.julia.commands;

import org.nure.julia.generator.jobs.JobFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class JobManagementCommands {
    private final JobFactory jobFactory;

    public JobManagementCommands(JobFactory jobFactory) {
        this.jobFactory = jobFactory;
    }

    @ShellMethod(key = {"job-stop"}, value = "Stops running job")
    public void stopJob(String batchId, String jobId) {
        jobFactory.stopJob(batchId, jobId);
    }

    @ShellMethod(key = {"job-resume"}, value = "Resume a paused job job")
    public void resumeJob(String batchId, String jobId) {
        jobFactory.resumeJob(batchId, jobId);
    }

    @ShellMethod(key = {"job-status"}, value = "Resume a paused job job")
    public String getJobStatus(String batchId, String jobId) {
        return jobFactory.getJobStatus(batchId, jobId).name();
    }

}
