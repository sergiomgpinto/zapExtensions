package org.zaproxy.addon.profilingproxy.metrics;

import org.junit.jupiter.api.Test;
import org.zaproxy.addon.profilingproxy.Message;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * This class checks if the SizesPerformanceMetric class construct the correct summary of all size metrics.
 */
class ResponseSizeMetricUnitTest {

    /**
     * This method verifies that the report string generated is correct.
     */
    @Test
    void responseSizeMetricUnitTest() {
        Metric metric = new ResponseSizeMetric();
        Message msg1 = new Message();
        msg1.setResponseSize(1250);
        msg1.setRequestSize(120);
        List<Message> messages = new ArrayList<>();
        messages.add(msg1);

        MetricDTO result = metric.getResult(messages);
        MetricDTO expectedResult = new MetricDTO(1250,
                1250,
                1250,
                1250,
                1250,
                1250,
                0);
        expectedResult.setIsResponseSize(true);

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
