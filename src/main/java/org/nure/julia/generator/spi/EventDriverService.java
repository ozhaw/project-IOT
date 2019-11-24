package org.nure.julia.generator.spi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nure.julia.events.events.DeviceAddedEvent;
import org.nure.julia.events.events.JobFinishedEvent;
import org.nure.julia.generator.DeviceService;
import org.nure.julia.generator.jobs.JobFactory;
import org.nure.julia.misc.DeviceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventDriverService {
    private final DeviceService deviceService;
    private final JobFactory jobFactory;
    private final List<String> results = new ArrayList<>();

    @Autowired
    public EventDriverService(DeviceService deviceService, JobFactory jobFactory) {
        this.deviceService = deviceService;
        this.jobFactory = jobFactory;
    }

    @EventListener(condition = "@deviceService.autoRegisterEnabled " +
            "and @deviceService.getDeviceStatus(#event.deviceId).name() == 'NEW'")
    public void deviceAddedEvent(DeviceAddedEvent event) {
        deviceService.setDeviceStatus(event.getDeviceId(), DeviceStatus.IN_USE);
        jobFactory.registerDevice(event.getDeviceId());
    }

    @EventListener(condition = "@jobFactory.autoBatchResultSendEnabled")
    public void jobFinishedEvent(JobFinishedEvent event) throws JsonProcessingException {
        results.add(new ObjectMapper().writeValueAsString(event.getSource()));
    }
}
