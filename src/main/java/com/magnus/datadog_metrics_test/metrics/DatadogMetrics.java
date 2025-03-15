package com.magnus.datadog_metrics_test.metrics;

import com.timgroup.statsd.NonBlockingStatsDClientBuilder;
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

    public void incrementCounterMicrometer(ContextRefreshedEvent event) {
        log.info("Strating count increment to send 1 million metrics to Datadog");
        IntStream.range(0, 1000000).parallel().forEach(i -> meterRegistry.counter("test.counter.million").increment());
        log.info("Finished count increment to send 1 million metrics to Datadog");
    }

    @EventListener
    public void incrementCounterDogStatsd(ContextRefreshedEvent event) {
        var instant = new StopWatch();
        log.info("Start sending");
        instant.start();
        StatsDClient statsd = new NonBlockingStatsDClientBuilder()
                .prefix("statsd")
                .hostname("localhost")
                .port(8125)
                .queueSize(550000)
                .senderWorkers(10)
                .threadFactory(Thread.ofVirtual().factory())
                .build();
        IntStream.range(0, 1000000).parallel().forEach(i -> statsd.increment("test.counter.datadog.statsd.million"));
        instant.stop();
        log.info("Finished sending in {} ms", instant.getTotalTimeMillis());
    }
}
