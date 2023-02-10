package org.zaproxy.addon.attackprevention;

import org.apache.commons.httpclient.URI;
import org.junit.jupiter.api.Test;
import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.addon.attackprevention.parser.FormParser;
import org.zaproxy.addon.attackprevention.parser.Parser;

/**
 * This class incorporates unit tests to verify
 * that the core domain names are being correctly
 * extracted.
 */
class UrlCleanerUnitTest {

    /**
     * This method verifies that the core domains of several urls are being correctly extracted.
     */
    @Test
    void cleanedUrlIsCorrect() {
        HttpMessage msg = new HttpMessage();
        Parser parser = new FormParser();

        URI uri1;
        URI uri2;
        URI uri3;
        URI uri4;
        URI uri5;
        URI uri6;
        URI uri7;

        try {
            uri1 = new URI("https://www.google.com", true);
            uri2 = new URI("https://www.google.com/sdgfhfg/asfggfh/ertdfghtre", true);
            uri3 = new URI("https://www.google.com?key=value", true);
            uri4 = new URI("http://www.google.com", true);
            uri5 = new URI("https://w.e.m", true);
            uri6 = new URI("https://w.e", true);
            uri7 = new URI("https://..", true);

        } catch (Exception e) {
            return;
        }

        try {
            msg.getRequestHeader().setURI(uri1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert (parser.parseMessage(msg)).getCleanedUrl().equals("https://www.google.com");

        try {
            msg.getRequestHeader().setURI(uri2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert (parser.parseMessage(msg)).getCleanedUrl().equals("https://www.google.com");

        try {
            msg.getRequestHeader().setURI(uri3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert (parser.parseMessage(msg)).getCleanedUrl().equals("https://www.google.com");

        try {
            msg.getRequestHeader().setURI(uri4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert (parser.parseMessage(msg)).getCleanedUrl().equals("http://www.google.com");

        try {
            msg.getRequestHeader().setURI(uri5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert (parser.parseMessage(msg)).getCleanedUrl().equals("https://w.e.m");

        try {
            msg.getRequestHeader().setURI(uri6);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert (parser.parseMessage(msg)).getCleanedUrl() == null;

        try {
            msg.getRequestHeader().setURI(uri7);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert (parser.parseMessage(msg)).getCleanedUrl().equals("https://..");
    }
}
