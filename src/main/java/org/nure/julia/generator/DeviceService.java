package org.nure.julia.generator;

import org.nure.julia.misc.DeviceStatus;

import java.util.Set;

public interface DeviceService {
    DeviceStatus addDevice(String deviceId);

    void addDevices(Set<String> deviceIds);

    Set<String> getDeviceIds();

    Set<String> getDeviceIdsWithStatus(DeviceStatus deviceStatus);

    boolean exists(String deviceId);

    boolean remove(String deviceId);
}
