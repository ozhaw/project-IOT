package org.nure.julia.dto;

import java.util.Date;

public class HealthInfoDto implements SourceDto {
    private Date auditDate;
    private String healthStatus;
    private String deviceId;

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String getSource() {
        return "health";
    }
}
