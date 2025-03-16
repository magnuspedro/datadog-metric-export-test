package com.magnus.datadog_metrics_test.metrics;

import com.timgroup.statsd.StatsDClient;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.stream.IntStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatadogMetrics {
    private final MeterRegistry meterRegistry;
    private final StatsDClient statsDClient;

    public void incrementCounterMicrometer() {
        var instant = new StopWatch();
        log.info("Start sending Micrometer StatsD");
        instant.start();
        IntStream.range(0, 1_000_000_0).parallel().forEach(i -> meterRegistry.counter("test.counter.million").increment());
        instant.stop();
        log.info("Finished sending in {} ms Micrometer StatsD", instant.getTotalTimeMillis());
    }

    public void incrementCounterDogStatsd() {
        var instant = new StopWatch();
        log.info("Start sending DogStatsD");
        instant.start();
        IntStream.range(0, 1_000_000_0).parallel().forEach(i -> statsDClient.increment("test.counter.datadog.statsd.million"));
        instant.stop();
        log.info("Finished sending in {} ms DogStatsD", instant.getTotalTimeMillis());
    }
}
