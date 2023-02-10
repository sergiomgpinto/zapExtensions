package org.zaproxy.addon.attackprevention.parser;

import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.addon.attackprevention.Request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This abstract class is the abstraction to a parser
 * for this extension.
 *
 * @see FormParser
 * @see JSONParser
 */
public abstract class Parser {

    /**
     * This method parses from httpmessage format to request format.
     * @return Request - the parsed request.
     */
    public Request parseMessage(HttpMessage msg) {
        String uri = msg.getRequestHeader().getURI().toString();
        String method = msg.getRequestHeader().getMethod();
        String body = msg.getRequestBody().toString();
        String username = parseUsername(body);
        String password = parsePassword(body);
        return new Request(uri, method, body, username, password);
    }

    abstract String parseUsername(String body);
    abstract String parsePassword(String body);
}
