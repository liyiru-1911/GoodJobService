package com.goodjob.distribution_center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableAutoConfiguration
@SpringBootApplication
public class DistributionCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributionCenterApplication.class, args);
    }

}
