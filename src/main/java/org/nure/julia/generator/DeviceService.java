package org.nure.julia.generator;

import java.util.Set;

public interface DeviceService {
    boolean addDevice(String deviceId);

    boolean addDevices(Set<String> deviceIds);

    Set<String> getDeviceIds();

    boolean exists(String deviceId);

    boolean remove(String deviceId);
}
