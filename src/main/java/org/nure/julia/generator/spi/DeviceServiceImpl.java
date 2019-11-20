package org.nure.julia.generator.spi;

import org.nure.julia.generator.DeviceService;
import org.nure.julia.misc.DeviceJobStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.ObjectUtils.allNotNull;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Service
public class DeviceServiceImpl implements DeviceService {
    private Map<String, DeviceJobStatus> deviceIds = new HashMap<>();

    @Override
    public DeviceJobStatus addDevice(String deviceId) {
        return defaultIfNull(deviceIds.put(deviceId, DeviceJobStatus.NEW), DeviceJobStatus.NEW);
    }

    @Override
    public void addDevices(Set<String> deviceIds) {
        this.deviceIds.putAll(deviceIds.stream()
                .collect(toMap(value -> value, value -> DeviceJobStatus.NEW)));
    }

    @Override
    public Set<String> getDeviceIds() {
        return this.deviceIds.keySet();
    }

    @Override
    public Set<String> getDeviceIdsWithStatus(DeviceJobStatus deviceJobStatus) {
        return this.deviceIds.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == deviceJobStatus)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean exists(String deviceId) {
        return deviceIds.containsKey(deviceId);
    }

    @Override
    public boolean remove(String deviceId) {
        return allNotNull(deviceIds.remove(deviceId));
    }
}
