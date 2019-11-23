package org.nure.julia;

import org.nure.julia.generator.jobs.entity.Job;
import org.nure.julia.generator.jobs.entity.spi.DeviceInfoJob;
import org.nure.julia.generator.jobs.entity.spi.HealthInfoJob;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@SpringBootApplication
public class ShellApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShellApplication.class, args);
    }

    @Bean
    public Function<String, Job> deviceInfoJobConstructor() {
        return DeviceInfoJob::new;
    }

    @Bean
    public Function<String, Job> healthInfoJobConstructor() {
        return HealthInfoJob::new;
    }

}
