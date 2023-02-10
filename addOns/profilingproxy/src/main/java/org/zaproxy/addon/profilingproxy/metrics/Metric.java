package org.zaproxy.addon.profilingproxy.metrics;

import org.zaproxy.addon.profilingproxy.Message;
import org.zaproxy.addon.profilingproxy.utils.MathUtils;

import java.util.List;

/**
 * This class is an interface for all the metrics
 *
 * @see RequestSizeMetric
 * @see RequestSizeMetric
 * @see ResponseSizeMetric
 * @see ResponseTimeMetric
 */
public abstract class Metric {
    List<Integer> measures;

    /**
     * Returns all results in a single string.
     * @param messages:  messages to calculate the statistics for
     * @return : a string containting all results
     */
    public abstract MetricDTO getResult(List<Message> messages);

    public void setMeasures(List<Integer> measures) {
        this.measures = measures;
    }

    /**
     * @return the minimum from the measures list
     */
    public int getMin() {
        return MathUtils.minimum(measures.stream().mapToInt(Integer::intValue));
    }

    /**
     * @return the maximum from the measures list
     */
    public int getMax() {
        return MathUtils.maximum(measures.stream().mapToInt(Integer::intValue));
    }

    /**
     * @return the average from the messages
     */
    public double getAverage() {
        return MathUtils.average(measures.stream().mapToInt(Integer::intValue));
    }

    /**
     * @return the median from the measures list
     */
    public double getMedian() {
        return MathUtils.median(measures.stream().mapToInt(Integer::intValue).sorted().toArray());
    }

    /**
     * @return the nth quartile from the measures list
     */
    public double getNthQuartile(int n){
        return MathUtils.nthQuartile(n,measures.stream().mapToInt(Integer::intValue).sorted().toArray());
    }

    /**
     * @return the standard deviation from the measures list
     */
    public double getStdDev() {
        return MathUtils.standardDeviation(measures.stream().mapToInt(Integer::intValue).toArray());}

    /**
     * Add Extra Stats to the result
     */
    protected MetricDTO performCalculations(){

        return new MetricDTO(getMin(),
                getMax(),
                getAverage(),
                getMedian(),
                getNthQuartile(1),
                getNthQuartile(3),
                getStdDev());
    }
}
