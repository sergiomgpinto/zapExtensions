package org.zaproxy.addon.profilingproxy;

/**
 * This class is the abstraction for the
 * profiling proxy extension to the
 * request and response messages
 * where the needed information
 * is stored.
 */
public class Message {

    private int requestSize;
    private int responseSize;
    private int responseTime;
    private String url;
    private byte[] responseBody = new byte[0];
    private boolean isCSS = false;
    private boolean isJS = false;
    private boolean isPNG = false;

    public Message() {}

    public Message(int requestSize, int responseSize, int responseTime, String url) {
        this.requestSize = requestSize;
        this.responseSize = responseSize;
        this.responseTime = responseTime;
        this.url = url;
    }

    public Message(int requestSize, int responseSize, int responseTime, String url, byte[] responseBody,
                   boolean isCSS, boolean isJS, boolean isPNG) {
        this.requestSize = requestSize;
        this.responseSize = responseSize;
        this.responseTime = responseTime;
        this.url = url;
        this.responseBody = responseBody;
        this.isCSS = isCSS;
        this.isJS = isJS;
        this.isPNG = isPNG;
    }

    public int getRequestSize() {
        return requestSize;
    }

    public void setRequestSize(int requestSize) {this.requestSize = requestSize;}

    public void setResponseSize(int responseSize) {
        this.responseSize = responseSize;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }

    public int getResponseSize() {
        return responseSize;
    }

    public int getResponseTime() {
        return responseTime;
    }

    public String getUrl() {
        return url;
    }

    public byte[] getResponseBody() {
        return responseBody;
    }

    public boolean isCSS() {
        return isCSS;
    }

    public boolean isJS() {
        return isJS;
    }

    public boolean isPNG() {
        return isPNG;
    }
}
