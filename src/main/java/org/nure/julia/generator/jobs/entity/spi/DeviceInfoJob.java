package org.nure.julia.generator.jobs.entity.spi;

import org.nure.julia.dto.DeviceInfoDto;
import org.nure.julia.generator.jobs.entity.Job;

public class DeviceInfoJob extends Job<DeviceInfoJob, DeviceInfoDto> {
    public DeviceInfoJob(String deviceId) {
        super(deviceId);
    }

    @Override
    public DeviceInfoDto execute() {
        return new DeviceInfoDto();
    }
}
