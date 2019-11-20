package org.nure.julia.misc;

import static java.lang.String.format;

public enum DeviceJobStatus {
    RUNNING("Job for device %s is running"),
    PAUSED("Job for device %s is paused"),
    DEAD("Job for device %s is dead"),
    NEW("Job for device %s is ready for actions"),
    UNKNOWN("%s");

    private String transitionMessage;

    DeviceJobStatus(String transitionMessage) {
        this.transitionMessage = transitionMessage;
    }

    public String getTransitionMessage(String deviceId) {
        return format(transitionMessage, deviceId);
    }
}
