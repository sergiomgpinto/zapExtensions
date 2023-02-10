package org.zaproxy.addon.filetester;

import org.parosproxy.paros.core.proxy.ProxyListener;
import org.parosproxy.paros.network.HttpMessage;

/**
 * This class inherits from the interface
 * ProxyListener and implements the logic
 * to retransmit the request for the
 * typosquatting manager to be handled
 * accordingly.
 *
 * @see ProxyListener
 */
public class FileTesterProxyListener implements ProxyListener {

    FileTesterManager fileTesterManager;

    public FileTesterProxyListener() {
        fileTesterManager = new FileTesterManager();
    }
    /**
     * This method is responsible for retransmitting
     * the browser request to the typosquatting
     * manager.
     *
     * @param msg - Request in the format of a
     * HttpMessage.
     * @return true if the message can be forwarded
     * else false.
     */
    @Override
    public boolean onHttpRequestSend(HttpMessage msg) {
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
    @Override
    public boolean onHttpResponseReceive(HttpMessage msg) {
        return fileTesterManager.scanFileDownload(msg);
    }

    /**
     * This method returns the order of when this
     * listener class should be notified.
     *
     * @return 0
     */
    @Override
    public int getArrangeableListenerOrder() {
        return 0;
    }
}
