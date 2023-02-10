package org.zaproxy.addon.profilingproxy.extensionapp;

import org.parosproxy.paros.core.proxy.ProxyListener;
import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.addon.profilingproxy.ProfilingProxyManager;
import org.zaproxy.addon.profilingproxy.extensionapp.ui.ProfilingProxyStatusPanel;

/**
 * This class is the listener for the profiling proxy that allows
 * to trigger the logic when a http message is sent and received.
 *
 * @see ProfilingProxyManager
 */
public class ProfilingProxyListener implements ProxyListener {

    private final ProfilingProxyManager manager;

    public ProfilingProxyListener(ProfilingProxyStatusPanel statusPanel) {
        this.manager = new ProfilingProxyManager(statusPanel);
    }

    /**
     * We set to the message its sent time so that later when we received its
     * response we can calculate the response time.
     * @param httpMessage the {@code HttpMessage}
     * that may be forwarded to the server
     */
    @Override
    public boolean onHttpRequestSend(HttpMessage httpMessage) {
        manager.setRequestSendTime(httpMessage);
        return true;
    }

    /**
     * We set to the message its response time, and we profile the message
     * to discover potential improvements and observe its metrics.
     * @param httpMessage the {@code HttpMessage}
     * that may be forwarded to the server
     */
    @Override
    public boolean onHttpResponseReceive(HttpMessage httpMessage) {
        manager.setResponseTime(httpMessage);
        manager.profileMessage(httpMessage);
        return true;
    }

    @SuppressWarnings("unused")
    @Override
    public int getArrangeableListenerOrder() {
        return 0;
    }
}
