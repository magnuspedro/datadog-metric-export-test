package com.magnus.datadog_metrics_test.metrics;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {
    private final DatadogMetrics datadogMetrics;

    @PostMapping("/micrometer")
    public void micrometer() {
        datadogMetrics.incrementCounterMicrometer();
    }

    @PostMapping("/dogstatsd")
    public void dogstatsd() {
        datadogMetrics.incrementCounterDogStatsd();
    }
}
