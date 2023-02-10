package org.zaproxy.addon.profilingproxy.extensionapp.ui;

import org.zaproxy.addon.profilingproxy.extensionapp.ui.metrics.MetricsPanelManager;
import org.zaproxy.addon.profilingproxy.extensionapp.ui.improvements.ImprovementsPanelManager;

/**
 * This interface creates the abstraction to manage
 * the status panel.
 *
 * @see MetricsPanelManager
 * @see ImprovementsPanelManager
 */
public interface StatusPanelManager {

    /**
     * This method will allow each manager update its corresponding
     * tab in the status panel.
     */
    void updatePanel();
}
