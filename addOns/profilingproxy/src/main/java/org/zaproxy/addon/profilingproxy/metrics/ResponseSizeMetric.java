package org.zaproxy.addon.profilingproxy.metrics;

import org.zaproxy.addon.profilingproxy.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * This class measures the response size performance.
 * This class inherits the Metric interface.
 *
 * @see Metric
 */
public class ResponseSizeMetric extends Metric {

    /**
     * Returns all response size statistics in a DTO.
     * @return the DTO containing the response size statistics.
     */
    @Override
    public MetricDTO getResult(List<Message> messages) {
        List<Integer> responseSizeStream = new ArrayList<>();

        for (Message message : messages) {
            int responseSize = message.getResponseSize();
            responseSizeStream.add(responseSize);
        }

        this.setMeasures(responseSizeStream);

        MetricDTO metric = this.performCalculations();
        metric.setIsResponseSize(true);

        return metric;
    }
}
