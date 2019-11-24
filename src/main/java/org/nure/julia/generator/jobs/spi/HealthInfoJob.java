package org.nure.julia.generator.jobs.spi;

import org.nure.julia.dto.HealthInfoDto;
import org.nure.julia.generator.jobs.Job;

public class HealthInfoJob extends Job<HealthInfoJob, HealthInfoDto> {
    public HealthInfoJob(String deviceId) {
        super(deviceId);
    }

    @Override
    public HealthInfoDto execute() {
        return new HealthInfoDto();
    }
}
