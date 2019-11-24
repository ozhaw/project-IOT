package org.nure.julia.events.events;

import org.springframework.context.ApplicationEvent;

public class DeviceAddedEvent extends ApplicationEvent {
    private String deviceId;

    public DeviceAddedEvent(Object source, String deviceId) {
        super(source);
        this.deviceId = deviceId;
    }

    public DeviceAddedEvent(Object source) {
        super(source);
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
