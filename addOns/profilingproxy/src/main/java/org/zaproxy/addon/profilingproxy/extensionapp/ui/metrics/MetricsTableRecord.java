package org.zaproxy.addon.profilingproxy.extensionapp.ui.metrics;

/**
 * This class is the abstraction to a record in the metrics table.
 **/
public class MetricsTableRecord {

    private final String statistic;
    private int requestSizeMetricValue;
    private int responseSizeMetricValue;
    private int responseTimeMetricValue;

    public MetricsTableRecord(String statistic, int requestSizeMetricValue, int responseSizeMetricValue, int responseTimeMetricValue) {
        this.statistic = statistic;
        this.requestSizeMetricValue = requestSizeMetricValue;
        this.responseSizeMetricValue = responseSizeMetricValue;
        this.responseTimeMetricValue = responseTimeMetricValue;
    }

    public String getStatistic() {
        return statistic;
    }

    public int getRequestSizeMetricValue() {
        return requestSizeMetricValue;
    }

    public int getResponseSizeMetricValue() {
        return responseSizeMetricValue;
    }

    public int getResponseTimeMetricValue() {
        return responseTimeMetricValue;
    }

    public void setRequestSizeMetricValue(int requestSizeMetricValue) {
        this.requestSizeMetricValue = requestSizeMetricValue;
    }

    public void setResponseSizeMetricValue(int responseSizeMetricValue) {
        this.responseSizeMetricValue = responseSizeMetricValue;
    }

    public void setResponseTimeMetricValue(int responseTimeMetricValue) {
        this.responseTimeMetricValue = responseTimeMetricValue;
    }
}
