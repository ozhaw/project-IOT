package org.nure.julia.misc;

import static java.lang.String.format;

public enum DeviceStatus {
    IN_USE("Device %s is used by job"),
    NEW("Device %s is ready for actions"),
    UNKNOWN("%s");

    private String transitionMessage;

    DeviceStatus(String transitionMessage) {
        this.transitionMessage = transitionMessage;
    }

    public String getTransitionMessage(String deviceId) {
        return format(transitionMessage, deviceId);
    }
}
