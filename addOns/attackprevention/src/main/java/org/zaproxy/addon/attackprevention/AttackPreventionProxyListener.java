package org.zaproxy.addon.attackprevention;

import org.parosproxy.paros.core.proxy.ProxyListener;
import org.parosproxy.paros.network.HttpMessage;

/**
 * This class inherits from the interface
 * ProxyListener and implements the logic
 * to retransmit the request for the
 * typosquatting and phishing managers to
 * be handled accordingly.
 *
 * @see ProxyListener
 * @see TyposquattingVulnerabilityResponseManager
 * @see PhishingVulnerabilityResponseManager
 */
public class AttackPreventionProxyListener implements ProxyListener {

    AttackPreventionResponseManager typosquattingManager = new TyposquattingVulnerabilityResponseManager();
    AttackPreventionResponseManager phishingManager = new PhishingVulnerabilityResponseManager();

    /**
     * This method is responsible for retransmitting
     * the browser request to the typosquatting
     * manager and phishing manager.
     *
     * @param msg - Request in the format of a
     * HttpMessage.
     * @return true if the message can be forwarded
     * else false.
     */
    @Override
    public boolean onHttpRequestSend(HttpMessage msg) {
        phishingManager.respond(msg);
        typosquattingManager.respond(msg);
        return true;
    }

    /**
     * This method is responsible for retransmitting
     * the request response to the browser.
     *
     * @param msg - Response in the format of a
     * HttpMessage.
     * @return true
     */
    @SuppressWarnings("unused")
    @Override
    public boolean onHttpResponseReceive(HttpMessage msg) {
        return true;
    }

    /**
     * This method returns the order of when this
     * listener class should be notified.
     *
     * @return 0
     */
    @SuppressWarnings("unused")
    @Override
    public int getArrangeableListenerOrder() {
        return 0;
    }
}
