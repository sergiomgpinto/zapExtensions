package org.zaproxy.addon.attackprevention;

import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.parosproxy.paros.network.HttpMalformedHeaderException;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpRequestHeader;

/**
 * The AttackManager interface allows for the creation
 * of new attack managers which handle requests that
 * may trigger actions, from which the user can benefit
 * from, in case an attack is detected.
 * The AttackManager interface provides a method called
 * respond where the logic for handling each attack
 * is implemented.
 *
 * @see TyposquattingVulnerabilityResponseManager
 * @see PhishingVulnerabilityResponseManager
 */
public abstract class AttackPreventionResponseManager {

    /**
     * This is the main method that handles the request
     * and implements the main logic.
     *
     * @param msg - The request.
     */
    abstract void respond(HttpMessage msg);

    /**
     * This is the main method that handles the request
     * and checks if it has any parameters in the url
     * to trigger specific domain logic.
     *
     * @param request - The request parsed in the domain
     * Request entity.
     */
    abstract boolean saveUserChoices(Request request);

    /**
     * This method sets the warning page to the browser
     * response.
     *
     * @param body - Contains the html for the warning page.
     */
    public void setMsgBody(HttpMessage msg, String body) throws HttpMalformedHeaderException {
        msg.setResponseHeader(msg.getRequestHeader().getVersion() + " 200 OK");
        msg.setResponseBody(body);
    }

    /**
     * This method sets the cleanedUri to this header's
     * request.
     *
     * @param cleanedUri - parsed URI from the browser request.
     * @throws URIException - if an error occurred while setting
     * the request URI.
     */
    public void setHeader(HttpMessage msg, String cleanedUri) throws URIException {
        URI uriObj = new URI(cleanedUri, true);
        HttpRequestHeader header = msg.getRequestHeader();
        header.setURI(uriObj);
    }
}
