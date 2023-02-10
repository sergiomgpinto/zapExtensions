package org.zaproxy.addon.profilingproxy.metrics;

import org.zaproxy.addon.profilingproxy.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * This class measures the size performance.

import java.util.ArrayList;
import java.util.List;

/**
 * This class measures the request size performance.
 * This class inherits the Metric interface.
 *
 * @see Metric
 */
public class RequestSizeMetric extends Metric {

    /**
     * Returns all response size statistics in a DTO.
     * @return the DTO containing the response size statistics.
     */
    @Override
    public MetricDTO getResult(List<Message> messages) {
        List<Integer> requestSizeStream = new ArrayList<>();

        for (Message message : messages) {
            int requestSize = message.getRequestSize();
            requestSizeStream.add(requestSize);
        }

        this.setMeasures(requestSizeStream);

        MetricDTO metric = this.performCalculations();
        metric.setIsRequestSize(true);

        return metric;
    }
}
