package org.nure.julia.generator.spi;

import org.nure.julia.events.DeviceAddedEvent;
import org.nure.julia.generator.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service("deviceService")
public class DeviceServiceImpl implements DeviceService {
    private final ApplicationEventPublisher publisher;

    private Set<String> deviceIds = new HashSet<>();

    @Autowired
    public DeviceServiceImpl(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public boolean addDevice(String deviceId) {
        this.publisher.publishEvent(new DeviceAddedEvent(deviceId, deviceId));

        return deviceIds.add(deviceId);
    }

    @Override
    public boolean addDevices(Set<String> deviceIds) {
        return deviceIds.stream()
                .filter(this::notExists)
                .allMatch(this::addDevice);
    }

    @Override
    public Set<String> getDeviceIds() {
        return this.deviceIds;
    }

    @Override
    public boolean exists(String deviceId) {
        return deviceIds.contains(deviceId);
    }

    private boolean notExists(String deviceId) {
        return !this.exists(deviceId);
    }

    @Override
    public boolean remove(String deviceId) {
        return deviceIds.remove(deviceId);
    }

}
