package org.nure.julia.generator;

import org.nure.julia.misc.DeviceJobStatus;

import java.util.Set;

public interface DeviceService {
    DeviceJobStatus addDevice(String deviceId);

    void addDevices(Set<String> deviceIds);

    Set<String> getDeviceIds();

    Set<String> getDeviceIdsWithStatus(DeviceJobStatus deviceJobStatus);

    boolean exists(String deviceId);

    boolean remove(String deviceId);
}
