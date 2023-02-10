package org.zaproxy.addon.profilingproxy.metrics;

import org.junit.jupiter.api.Test;
import org.zaproxy.addon.profilingproxy.Message;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class checks if the ResponseTimeMetric class construct the correct summary of all time metrics.
 */
class ResponseTimeMetricUnitTest {

    /**
     * This method verifies that the report string generated is correct.
     */
    @Test
    void responseTimeMetricUnitTest() {
        Metric metric = new ResponseTimeMetric();
        Message msg1 = new Message();
        msg1.setResponseTime(110);
        List<Message> messages = new ArrayList<>();
        messages.add(msg1);

        MetricDTO result = metric.getResult(messages);
        MetricDTO expectedResult = new MetricDTO(110,
                110,
                110,
                110,
                110,
                110,
                0);
        expectedResult.setIsResponseTime(true);

        assertEquals(result.getSmallest(), expectedResult.getSmallest());
        assertEquals(result.getFirstQuartile(), expectedResult.getFirstQuartile());
        assertEquals(result.getMedian(), expectedResult.getMedian());
        assertEquals(result.getAverage(), expectedResult.getAverage());
        assertEquals(result.getThirdQuartile(), expectedResult.getThirdQuartile());
        assertEquals(result.getLargest(), expectedResult.getLargest());
        assertEquals(result.getStandardDeviation(), expectedResult.getStandardDeviation());
        assertEquals(result.isRequestSize(), expectedResult.isRequestSize());
        assertEquals(result.isResponseSize(), expectedResult.isResponseSize());
        assertEquals(result.isResponseTime(), expectedResult.isResponseTime());
    }
}
