package org.nure.julia.generator.jobs.spi;

import me.xdrop.jrand.JRand;
import org.nure.julia.dto.DeviceInfoDto;
import org.nure.julia.generator.jobs.Job;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Date;

public class DeviceInfoJob extends Job<DeviceInfoDto> {
    private final String[] availableDeviceInfoStates = new String[]{"OK", "NDCHRG", "MLFUNC", "OFFLINE"};

    public DeviceInfoJob(String deviceId, String destination) {
        super(deviceId, destination);
    }

    @Override
    public DeviceInfoDto execute() {
        int nextDeviceStatus = JRand.natural().range(0, availableDeviceInfoStates.length - 1).gen();
        DeviceInfoDto deviceInfoDto = new DeviceInfoDto();
        deviceInfoDto.setDeviceStatus(availableDeviceInfoStates[nextDeviceStatus]);
        deviceInfoDto.setAuditDate(new Date());
        deviceInfoDto.setDeviceId(getDeviceId());
        return deviceInfoDto;
    }
}
