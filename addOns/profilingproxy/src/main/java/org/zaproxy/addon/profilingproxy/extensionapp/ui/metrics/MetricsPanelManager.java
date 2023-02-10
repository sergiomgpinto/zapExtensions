package org.zaproxy.addon.profilingproxy.extensionapp.ui.metrics;

import org.zaproxy.addon.profilingproxy.ProfilingProxyManager;
import org.zaproxy.addon.profilingproxy.extensionapp.ui.ProfilingProxyStatusPanel;
import org.zaproxy.addon.profilingproxy.extensionapp.ui.StatusPanelManager;
import org.zaproxy.addon.profilingproxy.metrics.MetricDTO;
import org.zaproxy.addon.profilingproxy.metrics.MetricsManager;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the metrics table in the status panel.
 *
 * @see StatusPanelManager
 */
public class MetricsPanelManager implements StatusPanelManager {

    private final ProfilingProxyStatusPanel statusPanel;
    private final List<MetricDTO> metricDTOs;

    public MetricsPanelManager(ProfilingProxyStatusPanel statusPanel) {
        this.statusPanel = statusPanel;
        metricDTOs = new ArrayList<>();
    }

    /**
     * This method updates the metrics table in the status panel for each
     * metric received. Will be three per message. One for request size,
     * another for response size and the last for response time.
     *
     * @see MetricDTO
     */
    @Override
    public void updatePanel() {
        for (MetricDTO metric : metricDTOs) {
            statusPanel.updateMetricsTable(metric);
        }
    }

    /**
     * This method sets the metrics DTOs coming as an output
     * from the metrics manager.
     *
     * @see MetricDTO
     * @see MetricsManager
     * @see ProfilingProxyManager
     */
    public void setMetricsDTO(List<MetricDTO> MetricDTO) {
        this.metricDTOs.clear();// Clean previous metrics since they were already displayed.
        this.metricDTOs.addAll(MetricDTO);
    }
}
