package org.nure.julia.generator.jobs.spi;

import me.xdrop.jrand.JRand;
import org.nure.julia.dto.HealthInfoDto;
import org.nure.julia.generator.jobs.Job;

import java.util.Date;

public class HealthInfoJob extends Job<HealthInfoDto> {
    private final String[] availableHealthInfoStates = new String[]{"OK", "NODATA", "DANGER", "CRITICAL"};

    public HealthInfoJob(String deviceId) {
        super(deviceId);
    }

    @Override
    public HealthInfoDto execute() {
        int nextDeviceStatus = JRand.natural().range(0, availableHealthInfoStates.length - 1).gen();
        HealthInfoDto healthInfoDto = new HealthInfoDto();
        healthInfoDto.setHealthStatus(availableHealthInfoStates[nextDeviceStatus]);
        healthInfoDto.setAuditDate(new Date());
        healthInfoDto.setDeviceId(getDeviceId());
        return healthInfoDto;
    }
}
