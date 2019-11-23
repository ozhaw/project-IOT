package org.nure.julia.misc;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.stream.Stream;

public enum JobStatus {
    NEW("READY"),
    READY("RUNNING"),
    RUNNING("PAUSED"),
    PAUSED("RUNNING", "READY");

    private String[] allowedTransitions;

    JobStatus(String... allowedTransitions) {
        this.allowedTransitions = allowedTransitions;
    }

    public boolean isTransitionAllowed(@NotNull final JobStatus jobStatus) {
        return Stream.of(allowedTransitions)
                .anyMatch(name -> name.equals(jobStatus.name()));
    }

    public String[] getAllowedTransitions() {
        return Arrays.copyOf(this.allowedTransitions, this.allowedTransitions.length);
    }
}
