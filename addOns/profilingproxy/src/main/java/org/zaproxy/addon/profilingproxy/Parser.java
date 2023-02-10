package org.zaproxy.addon.profilingproxy;

import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpRequestHeader;
import org.parosproxy.paros.network.HttpResponseHeader;
import org.zaproxy.zap.network.HttpRequestBody;
import org.zaproxy.zap.network.HttpResponseBody;

/**
 * This class is the abstraction for the
 * profiling proxy extension of a parser
 * which maps HttpMessage to domain
 * Message.
 *
 * @see Message
 */
public class Parser {

    /**
     * Parses a HttpMessage to a Message.
     *
     * @param msg - the HttpMessage containing the request and response.
     * @throws IllegalArgumentException - if the headers are invalid,
     * or the body contains data from more than one type of file.
     */
    public Message parseMessage(HttpMessage msg) throws IllegalArgumentException {

        HttpRequestHeader mReqHeader = msg.getRequestHeader();
        HttpRequestBody mReqBody = msg.getRequestBody();
        HttpResponseHeader mRespHeader = msg.getResponseHeader();
        HttpResponseBody mRespBody = msg.getResponseBody();

        if (mReqHeader.isEmpty() || mRespHeader.isEmpty())
            throw new IllegalArgumentException("An error occurred while parsing the message headers.");

        int requestSize = mReqHeader.toString().getBytes().length
                + mReqBody.toString().getBytes().length;
        int responseSize = mRespHeader.toString().getBytes().length
                + mRespBody.toString().getBytes().length;
        int responseTime = msg.getTimeElapsedMillis();
        String url = mReqHeader.getURI().toString();

        if (mRespBody.length() > 0) {
            boolean isCSS = mRespHeader.isCss();
            boolean isJS = mRespHeader.isJavaScript();

            boolean isPNG = mRespHeader.hasContentType("image/png");

            if (countFileTypes(isCSS, isJS, isPNG) > 1) {
                throw new IllegalArgumentException("An error occurred while " +
                        "parsing the response content. It includes more than one type of content" +
                        " file.");
            }
            else {
                return new Message(requestSize,
                        responseSize,
                        responseTime,
                        url,
                        mRespBody.getBytes(),
                        isCSS,
                        isJS,
                        isPNG);
            }
        }
        else {
            return new Message(requestSize,
                    responseSize,
                    responseTime,
                    url);
        }
    }

    /**
     * This method allows to verify that the response body
     * does not include more than one type of file.
     *
     * @param vars - the boolean variables to be counted.
     * @return the number of true variables.
     */
    private int countFileTypes(boolean... vars) {
        int count = 0;
        for (boolean variable : vars) {
            count += (variable ? 1 : 0);
        }
        return count;
    }

}
