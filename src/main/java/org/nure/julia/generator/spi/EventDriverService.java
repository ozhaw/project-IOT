package org.nure.julia.generator.spi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nure.julia.events.events.DeviceAddedEvent;
import org.nure.julia.events.events.JobAddedEvent;
import org.nure.julia.events.events.JobFinishedEvent;
import org.nure.julia.events.events.JobStatusChangedEvent;
import org.nure.julia.generator.jobs.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class EventDriverService {
    private final JobFactory jobFactory;
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public EventDriverService(JobFactory jobFactory, JmsTemplate jmsTemplate) {
        this.jobFactory = jobFactory;
        this.jmsTemplate = jmsTemplate;
    }

    @EventListener
    public void deviceAddedEvent(DeviceAddedEvent event) {
        System.out.println(format("Device %s was added", event.getDeviceId()));
        jobFactory.registerDevice(event.getDeviceId());
    }

    @EventListener
    public void jobFinishedEvent(JobFinishedEvent event) {
        try {
            jmsTemplate.convertAndSend(event.getDestination(), mapper.writeValueAsString(event.getSource()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("Unable to send job results to the Queue");
        }
    }

    @EventListener
    @Async
    public void jobStatusChangedEvent(JobStatusChangedEvent jobStatusChangedEvent) {
        System.out.println(format("Job %s now has status %s",
                jobStatusChangedEvent.getJobId(), jobStatusChangedEvent.getJobStatus().name()));
    }

    @EventListener
    @Async
    public void jobStatusChangedEvent(JobAddedEvent jobAddedEvent) {
        System.out.println(format("Job %s was added", jobAddedEvent.getJobId()));
    }
}
