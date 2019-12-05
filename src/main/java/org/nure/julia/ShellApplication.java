package org.nure.julia;

import org.nure.julia.generator.jobs.Job;
import org.nure.julia.generator.jobs.spi.DeviceInfoJob;
import org.nure.julia.generator.jobs.spi.HealthInfoJob;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.function.Function;

@SpringBootApplication
@EnableScheduling
@EnableJms
public class ShellApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShellApplication.class, args);
    }

    @Bean
    public Function<String, Job> deviceInfoJobConstructor() {
        return (deviceId) -> new DeviceInfoJob(deviceId, "app.device");
    }

    @Bean
    public Function<String, Job> healthInfoJobConstructor() {
        return (deviceId) -> new HealthInfoJob(deviceId, "app.health");
    }

}
