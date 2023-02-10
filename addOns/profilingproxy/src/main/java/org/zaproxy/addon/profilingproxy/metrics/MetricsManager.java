package org.zaproxy.addon.profilingproxy.metrics;

import org.zaproxy.addon.profilingproxy.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This class is the abstraction that is
 * responsible for managing the metrics
 * being run for each message.
 */
public class MetricsManager {

    List<Metric> metrics;

    public MetricsManager() {
        this.metrics = new ArrayList<>();
        setPerformanceMetrics();
    }

    /**
     * This method runs all the metrics
     * for all the previously stored
     * messages.
     *
     * @param messages - the list of stored messages.
     * @return the list of metrics DTOs with the
     * output information to be displayed.
     */
    public List<MetricDTO> runMetrics(List<Message> messages) {
        return metrics.stream().map(x -> x.getResult(messages)).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private void setPerformanceMetrics() {
        metrics.add(new RequestSizeMetric());
        metrics.add(new ResponseSizeMetric());
        metrics.add(new ResponseTimeMetric());
    }
}
