package com.magnus.datadog_metrics_test.config;

import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDAggregator;
import com.timgroup.statsd.Telemetry;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "management.dogstatsd.metrics.export")
public class DogStatsDProperties {
    private int maxPacketSizeBytes = 0;
    private int port = NonBlockingStatsDClient.DEFAULT_DOGSTATSD_PORT;
    private int telemetryPort = NonBlockingStatsDClient.DEFAULT_DOGSTATSD_PORT;
    private int queueSize = NonBlockingStatsDClient.DEFAULT_QUEUE_SIZE;
    private int timeout = NonBlockingStatsDClient.SOCKET_TIMEOUT_MS;
    private int bufferPoolSize = NonBlockingStatsDClient.DEFAULT_POOL_SIZE;
    private int socketBufferSize = NonBlockingStatsDClient.SOCKET_BUFFER_BYTES;
    private int processorWorkers = NonBlockingStatsDClient.DEFAULT_PROCESSOR_WORKERS;
    private int senderWorkers = NonBlockingStatsDClient.DEFAULT_SENDER_WORKERS;
    private boolean blocking = NonBlockingStatsDClient.DEFAULT_BLOCKING;
    private boolean enableTelemetry = NonBlockingStatsDClient.DEFAULT_ENABLE_TELEMETRY;
    private boolean enableAggregation = NonBlockingStatsDClient.DEFAULT_ENABLE_AGGREGATION;
    private int telemetryFlushInterval = Telemetry.DEFAULT_FLUSH_INTERVAL;
    private int aggregationFlushInterval = StatsDAggregator.DEFAULT_FLUSH_INTERVAL;
    private int aggregationShards = StatsDAggregator.DEFAULT_SHARDS;
    private boolean originDetectionEnabled = NonBlockingStatsDClient.DEFAULT_ENABLE_ORIGIN_DETECTION;
    private int connectionTimeout = NonBlockingStatsDClient.SOCKET_CONNECT_TIMEOUT_MS;
    private String hostname = "localhost";
    private String telemetryHostname;
    private String prefix;
    private String namedPipe;
    private String entityID;
    private String containerID;
    private Map<String, String> tags;
    private boolean enabled = true;

    public String[] getConstantTags() {
        return tags.entrySet().stream()
                .filter(e -> e.getKey() != null && e.getValue() != null)
                .map(e -> e.getKey() + ":" + e.getValue())
                .toArray(String[]::new);
    }
}
