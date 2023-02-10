package org.zaproxy.addon.profilingproxy.extensionapp.ui.metrics;

import org.zaproxy.addon.profilingproxy.extensionapp.ui.StatusPanelTableModel;
import org.zaproxy.addon.profilingproxy.metrics.MetricDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the table model for the metrics panel.
 *
 * @see StatusPanelTableModel
 */
public class MetricsPanelTableModel extends StatusPanelTableModel {

    static final long serialVersionUID = 1L;
    private static final String[] COLUMN_NAMES = {
            "",
            "Request Size(Bytes)",
            "Response Size(Bytes)",
            "Response Time(Milliseconds)",
    };
    private final List<MetricsTableRecord> data = new ArrayList<>();

    public MetricsPanelTableModel() {
        super();
        data.add(new MetricsTableRecord("Smallest", 0, 0, 0));
        data.add(new MetricsTableRecord("Largest", 0, 0, 0));
        data.add(new MetricsTableRecord("Average", 0, 0, 0));
        data.add(new MetricsTableRecord("Median", 0, 0, 0));
        data.add(new MetricsTableRecord("First Quartile", 0, 0, 0));
        data.add(new MetricsTableRecord("Third Quartile", 0, 0, 0));
        data.add(new MetricsTableRecord("Standard Deviation", 0, 0, 0));
        fireTableDataChanged();
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    /**
     * This method extracts the value in the metrics table
     * at the given row and column.
     *
     * @param row - the row of the table
     * @param column - the column of the table
     * @return the value at the given row and column
     */
    @Override
    public Object getValueAt(int row, int column) {
        if (row > data.size()) {
            return null;
        }
        if (column == 0) {
            return data.get(row).getStatistic();
        }
        else if (column == 1) {
            return data.get(row).getRequestSizeMetricValue();
        }
        else if (column == 2) {
            return data.get(row).getResponseSizeMetricValue();
        }
        else if (column == 3) {
            return data.get(row).getResponseTimeMetricValue();
        }
        return null;
    }

    /**
     * This method updates a column in the metrics table to the
     * specific type of metric for all the statistics.
     * MetricDTO metric: metric containing all measures
     */
    public void updateColumn(MetricDTO metric) {
        List<Integer> measures=metric.getMeasures();
        if(metric.isRequestSize()) {
            for (int i = 0; i < measures.size(); i++) {
                data.get(i).setRequestSizeMetricValue(measures.get(i));
            }
        }
            else if(metric.isResponseSize()){
                for(int i=0;i<measures.size();i++){
                    data.get(i).setResponseSizeMetricValue(measures.get(i));
            }
        }
            else if(metric.isResponseTime()){
            for(int i=0;i<measures.size();i++){
                data.get(i).setResponseTimeMetricValue(measures.get(i));
            }
            }

        fireTableDataChanged();
    }
}
