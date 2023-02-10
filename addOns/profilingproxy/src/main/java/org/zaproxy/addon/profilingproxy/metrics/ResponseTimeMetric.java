package org.zaproxy.addon.profilingproxy.metrics;

import org.zaproxy.addon.profilingproxy.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * This class measures the time performance.
 * This class inherits the Metric interface.
 *
 * @see Metric
 */
public class ResponseTimeMetric extends Metric {

    /**
     * Returns all response time statistics in a DTO.
     * @return the DTO containing the response size statistics.
     */
    @Override
    public MetricDTO getResult(List<Message> messages) {
        List<Integer> requestTimesStream = new ArrayList<>();

        for (Message message : messages) {
            int responseTime = message.getResponseTime();
            requestTimesStream.add(responseTime);
        }

        this.setMeasures(requestTimesStream);

        MetricDTO metric = this.performCalculations();
        metric.setIsResponseTime(true);

        return metric;
    }
}
