package org.nure.julia.generator.spi;

import org.nure.julia.events.events.DeviceAddedEvent;
import org.nure.julia.generator.DeviceService;
import org.nure.julia.misc.DeviceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.ObjectUtils.allNotNull;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Service("deviceService")
public class DeviceServiceImpl implements DeviceService {
    private final ApplicationEventPublisher publisher;

    private Map<String, DeviceStatus> deviceIds = new HashMap<>();

    @Value("${application.events.auto-device-register}")
    private boolean autoRegisterEnabled;

    @Autowired
    public DeviceServiceImpl(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public DeviceStatus addDevice(String deviceId) {
        DeviceStatus deviceStatus = defaultIfNull(deviceIds.put(deviceId, DeviceStatus.NEW), DeviceStatus.NEW);
        this.publisher.publishEvent(new DeviceAddedEvent(DeviceStatus.NEW, deviceId));
        return deviceStatus;
    }

    @Override
    public DeviceStatus getDeviceStatus(@NotNull String deviceId) {
        return deviceIds.getOrDefault(deviceId, DeviceStatus.UNKNOWN);
    }

    @Override
    public DeviceStatus setDeviceStatus(@NotNull String deviceId, @NotNull DeviceStatus deviceStatus) {
        return getDeviceStatus(deviceId) != DeviceStatus.UNKNOWN
                ? deviceIds.put(deviceId, deviceStatus)
                : DeviceStatus.UNKNOWN;
    }

    @Override
    public void addDevices(Set<String> deviceIds) {
        this.deviceIds.putAll(deviceIds.stream()
                .collect(toMap(value -> value, value -> DeviceStatus.NEW)));
    }

    @Override
    public Set<String> getDeviceIds() {
        return this.deviceIds.keySet();
    }

    @Override
    public Set<String> getDeviceIdsWithStatus(DeviceStatus deviceStatus) {
        return this.deviceIds.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == deviceStatus)
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

    public boolean isAutoRegisterEnabled() {
        return autoRegisterEnabled;
    }
}
