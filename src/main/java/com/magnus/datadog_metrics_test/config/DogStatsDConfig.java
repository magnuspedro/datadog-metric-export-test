package com.magnus.datadog_metrics_test.config;

import com.timgroup.statsd.NoOpStatsDClient;
import com.timgroup.statsd.NonBlockingStatsDClientBuilder;
import com.timgroup.statsd.StatsDClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DogStatsDConfig {

    @Bean
    public StatsDClient dogStatsDClient(DogStatsDProperties dogStatsDProperties, @Value("${spring.threads.virtual.enabled:false}") boolean virtualThreadsEnabled) {
        if (!dogStatsDProperties.isEnabled()) {
            return new NoOpStatsDClient();
        }
        var client = new NonBlockingStatsDClientBuilder()
                .maxPacketSizeBytes(dogStatsDProperties.getMaxPacketSizeBytes())
                .port(dogStatsDProperties.getPort())
                .telemetryHostname(dogStatsDProperties.getTelemetryHostname())
                .queueSize(dogStatsDProperties.getQueueSize())
                .timeout(dogStatsDProperties.getTimeout())
                .bufferPoolSize(dogStatsDProperties.getBufferPoolSize())
                .socketBufferSize(dogStatsDProperties.getSocketBufferSize())
                .processorWorkers(dogStatsDProperties.getProcessorWorkers())
                .senderWorkers(dogStatsDProperties.getSenderWorkers())
                .blocking(dogStatsDProperties.isBlocking())
                .enableTelemetry(dogStatsDProperties.isEnableTelemetry())
                .enableAggregation(dogStatsDProperties.isEnableAggregation())
                .telemetryFlushInterval(dogStatsDProperties.getTelemetryFlushInterval())
                .aggregationFlushInterval(dogStatsDProperties.getAggregationFlushInterval())
                .aggregationShards(dogStatsDProperties.getAggregationShards())
                .originDetectionEnabled(dogStatsDProperties.isOriginDetectionEnabled())
                .connectionTimeout(dogStatsDProperties.getConnectionTimeout())
                .hostname(dogStatsDProperties.getHostname())
                .telemetryHostname(dogStatsDProperties.getTelemetryHostname())
                .namedPipe(dogStatsDProperties.getNamedPipe())
                .prefix(dogStatsDProperties.getPrefix())
                .entityID(dogStatsDProperties.getEntityID())
                .constantTags(dogStatsDProperties.getConstantTags())
                .containerID(dogStatsDProperties.getContainerID());

        if (virtualThreadsEnabled) {
            client.threadFactory(Thread.ofVirtual().factory());
        }

        return client.build();
    }
}
