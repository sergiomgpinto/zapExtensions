package org.zaproxy.addon.profilingproxy;

import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.addon.profilingproxy.extensionapp.ui.ProfilingProxyStatusPanel;
import org.zaproxy.addon.profilingproxy.extensionapp.ui.improvements.ImprovementsPanelManager;
import org.zaproxy.addon.profilingproxy.extensionapp.ui.metrics.MetricsPanelManager;
import org.zaproxy.addon.profilingproxy.improvements.ImprovementDTO;
import org.zaproxy.addon.profilingproxy.improvements.ImprovementsManager;
import org.zaproxy.addon.profilingproxy.metrics.MetricDTO;
import org.zaproxy.addon.profilingproxy.metrics.MetricsManager;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the class that delegates the work to the other classes
 * where the messaged will be processed against the potential
 * improvements and metrics and the results stored.
 */
public class ProfilingProxyManager {

    private final MetricsManager metricsManager;
    private final ImprovementsManager improvementsManager;
    private final Parser parser;
    private final List<Message> messages;
    private final MetricsPanelManager metricsPanelManager;
    private final ImprovementsPanelManager improvementsPanelManager;

    public ProfilingProxyManager(ProfilingProxyStatusPanel statusPanel) {
        parser = new Parser();
        messages= new ArrayList<>();
        metricsManager = new MetricsManager();
        improvementsManager = new ImprovementsManager();
        metricsPanelManager = new MetricsPanelManager(statusPanel);
        improvementsPanelManager = new ImprovementsPanelManager(statusPanel);
    }

    /**
     * Applies the profiling proxy logic to the message.
     *
     * @param httpMessage - the HttpMessage to be profiled.
     */
    public void profileMessage(HttpMessage httpMessage) {
        try {
            Message message = parser.parseMessage(httpMessage);

            messages.add(message);

            List<MetricDTO> metrics = metricsManager.runMetrics(messages);

            metricsPanelManager.setMetricsDTO(metrics);
            metricsPanelManager.updatePanel();

            List<ImprovementDTO> improvements = improvementsManager.runImprovements(message);

            improvementsPanelManager.setImprovementsDTO(improvements);
            improvementsPanelManager.updatePanel();

        } catch (IllegalArgumentException e) {
            System.out.println("Error parsing message. " + e.getMessage());
        }
    }

    /**
     * This method attaches to the HttpMessage its send time
     * so that when we receive the answer from the server as
     * an answer we can calculate the response time.
     *
     * @param msg - the HttpMessage from the client side.
     */
    public void setRequestSendTime(HttpMessage msg) {
        msg.setTimeSentMillis(System.currentTimeMillis());
    }

    /**
     * This method attaches to the HttpMessage its response
     * time.
     *
     * @param msg - the HttpMessage from the server side.
     * @throws IllegalArgumentException - if the HttpMessage
     * has an invalid send time.
     */
    public void setResponseTime(HttpMessage msg) throws IllegalArgumentException{
        long requestSendTime = msg.getTimeSentMillis();
        long elapsedTime = System.currentTimeMillis() - requestSendTime;

        if (requestSendTime > 0 && elapsedTime >= 0) {
            msg.setTimeElapsedMillis((int) elapsedTime);
        } else {
            throw new IllegalArgumentException("An error occurred while " +
                    "setting the response receive time.");
        }
    }
}
