package org.nure.julia.dto;

import java.util.Date;

public class JobLogDto {
    private String jobId;
    private Date runDate;
    private boolean successful;

    public JobLogDto(String jobId, Date runDate, boolean successful) {
        this.jobId = jobId;
        this.runDate = runDate;
        this.successful = successful;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public Date getRunDate() {
        return runDate;
    }

    public void setRunDate(Date runDate) {
        this.runDate = runDate;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }
}
