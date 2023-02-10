package org.zaproxy.addon.profilingproxy.metrics;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the abstraction to a data transfer object for an improvement.
 * It is responsible for transferring the return of a metric to a given
 * message to the UI.
 */
public class MetricDTO {

    private final int smallest;
    private final int largest;
    private final double average;
    private final double median;
    private final double firstQuartile;
    private final double thirdQuartile;
    private final double standardDeviation;
    private boolean isRequestSize;
    private boolean isResponseSize;
    private boolean isResponseTime;
    private final List<Integer> measures;
    public MetricDTO(int smallest, int largest, double average, double median, double firstQuartile, double thirdQuartile, double standardDeviation) {
        this.smallest = smallest;
        this.largest = largest;
        this.average = average;
        this.median = median;
        this.firstQuartile = firstQuartile;
        this.thirdQuartile = thirdQuartile;
        this.standardDeviation = standardDeviation;

        measures=new ArrayList<>();
        measures.add(smallest);
        measures.add(largest);
        measures.add((int)average);
        measures.add((int)median);
        measures.add((int)firstQuartile);
        measures.add((int)thirdQuartile);
        measures.add((int)standardDeviation);
    }

    public int getSmallest() {
        return smallest;
    }

    public int getLargest() {
        return largest;
    }

    public double getAverage() {
        return average;
    }

    public double getMedian() {
        return median;
    }

    public double getFirstQuartile() {
        return firstQuartile;
    }

    public double getThirdQuartile() {
        return thirdQuartile;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

    public boolean isRequestSize() {
        return isRequestSize;
    }

    public boolean isResponseSize() {
        return isResponseSize;
    }

    public boolean isResponseTime() {
        return isResponseTime;
    }

    public void setIsResponseTime(boolean isResponseTime) {
        this.isResponseTime = isResponseTime;
    }

    public void setIsResponseSize(boolean isResponseSize) {
        this.isResponseSize = isResponseSize;
    }

    public void setIsRequestSize(boolean isRequestSize) {
        this.isRequestSize = isRequestSize;
    }

    public List<Integer> getMeasures() {
        return measures;
    }
}
