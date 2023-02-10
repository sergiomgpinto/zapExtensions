package org.zaproxy.addon.profilingproxy.extensionapp.ui.improvements;

import org.zaproxy.addon.profilingproxy.extensionapp.ui.ProfilingProxyStatusPanel;
import org.zaproxy.addon.profilingproxy.extensionapp.ui.StatusPanelManager;
import org.zaproxy.addon.profilingproxy.improvements.ImprovementDTO;
import org.zaproxy.addon.profilingproxy.metrics.MetricDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the improvements table in the status panel.
 *
 * @see StatusPanelManager
 */
public class ImprovementsPanelManager implements StatusPanelManager {

    private final ProfilingProxyStatusPanel statusPanel;

    private final List<ImprovementDTO> improvements;

    public ImprovementsPanelManager(ProfilingProxyStatusPanel statusPanel) {
        this.statusPanel = statusPanel;
        improvements = new ArrayList<>();
    }

    /**
     * This method updates the improvements table in the status panel for each
     * improvement received. Will be at most two improvements.
     *
     * @see MetricDTO
     */
    @Override
    public void updatePanel() {
        for (ImprovementDTO improvement : improvements) {
            statusPanel.updateImprovementsTable(improvement);
        }
    }

    /**
     * This method updates the improvements table in the status panel for each
     * improvement received. Will be at most two improvements per message.
     *
     * @see ImprovementDTO
     */
    public void setImprovementsDTO(List<ImprovementDTO> improvements) {
        this.improvements.clear();// Clean previous improvements since they were already displayed.
        for (ImprovementDTO improvement : improvements) {
            if (improvement.isImprovementIsPossible()) {
                this.improvements.add(improvement);
            }
        }
    }
}
