package org.nure.julia.generator.jobs.entity.spi;

import org.nure.julia.dto.HealthInfoDto;
import org.nure.julia.generator.jobs.entity.Job;

public class HealthInfoJob extends Job<HealthInfoJob, HealthInfoDto> {
    public HealthInfoJob(String deviceId) {
        super(deviceId);
    }

    @Override
    public HealthInfoDto execute() {
        return new HealthInfoDto();
    }
}
