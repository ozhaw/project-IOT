package org.nure.julia.generator.spi;

import org.nure.julia.events.events.DeviceAddedEvent;
import org.nure.julia.generator.DeviceService;
import org.nure.julia.generator.jobs.JobFactory;
import org.nure.julia.misc.DeviceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class EventDriverService {
    private final DeviceService deviceService;
    private final JobFactory jobFactory;

    @Autowired
    public EventDriverService(DeviceService deviceService, JobFactory jobFactory) {
        this.deviceService = deviceService;
        this.jobFactory = jobFactory;
    }

    @EventListener(condition = "@deviceService.autoRegisterEnabled " +
            "and @deviceService.getDeviceStatus(#event.deviceId).name() == 'NEW'")
    public void deviceAddedEvent(DeviceAddedEvent event) {
        deviceService.setDeviceStatus(event.getDeviceId(), DeviceStatus.IN_USE);
        //jobFactory.registerDevice(event.getDeviceId());
    }
}
