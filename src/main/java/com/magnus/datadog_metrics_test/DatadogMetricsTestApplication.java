package com.magnus.datadog_metrics_test;

import com.magnus.datadog_metrics_test.config.DogStatsDProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(DogStatsDProperties.class)
public class DatadogMetricsTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatadogMetricsTestApplication.class, args);
	}

}
