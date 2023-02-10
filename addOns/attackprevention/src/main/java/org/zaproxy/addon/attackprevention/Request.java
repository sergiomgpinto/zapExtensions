package org.zaproxy.addon.attackprevention;

import org.parosproxy.paros.network.HttpRequestHeader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class parses the HttpRequest
 * to a domain logic request with
 * the information needed to verify
 * for vulnerabilities.
 *
 */
public class Request {

    private final String uri;
    private final String method;
    private final String body;
    private String username;
    private String password;

    public Request(String uri, String method, String body, String username, String password) {
        this.uri = uri;
        this.method = method;
        this.body = body;
        this.username = username;
        this.password = password;
    }

    /**
     * This method sets the user username for the
     * login request.
     *
     * @param username - the user username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This method gets the user username from the
     * login request.
     *
     * @return the user username.
     */
    public String getUsername() {
        return this.username;
    }
    /**
     * This method sets the user password for the
     * login request.
     *
     * @param password - the user password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This method gets the user password from the
     * login request.
     *
     * @return the user password.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * This method gets the domain name from the browser
     * request.
     *
     * @return the domain name from the browser request.
     */
    public String getURL() {
        return this.uri;
    }

    /**
     * This method gets body from the browser request.
     *
     * @return the body from the browser request.
     */
    public String getBody() {
        return this.body;
    }

    /**
     * This method check if the url has a remember flag for the
     * typosquatting attack.
     *
     * @return  boolean True in case the user wants to save his
     * preference on to continue or not to the typosquatted
     * website, else false.
     */
    public boolean isToRememberUrl(){
        return this.uri.contains("action=remember");
    }

    /**
     * This method check if the url has a redirect flag or not
     * @return  boolean True in case the user wants to visit the url, else false
     */
    public boolean isToRedirectUrl() { return this.uri.contains("action=redirect"); }

    /**
     * This method checks if the request is a POST request.
     *
     * @return  true if the request is a POST request, else false
     */
    public boolean isPostRequest() {
        return this.method.equalsIgnoreCase(HttpRequestHeader.POST);
    }

    /**
     * This method allows to detect if this the request
     * after the user presses continue login on the
     * login warning page.
     *
     * @return  true if it is else false
     */
    public boolean isToContinueLogin() { return this.uri.contains("action=continue-login"); }

    /**
     * This method allows to detect if this the request
     * after the user presses cancel login on the
     * login warning page.
     *
     * @return  true if it is else false
     */
    public boolean isToCancelLogin() { return this.uri.contains("action=cancel-login"); }

    /**
     * This method allows to detect if this the request
     * after the user does not click on the tick box
     * to now show password warning again.
     *
     * @return  true if it is else false
     */
    public boolean isToContinueToShowPasswordWarning() {
        return this.uri.contains("action=continue-password-warning");
    }

    /**
     * This method allows to detect if this the request
     * after the user clicks on the tick box
     * to now show password warning again.
     *
     * @return  true if it is else false
     */
    public boolean isToNotShowAgainPasswordWarning() {
        return this.uri.contains("action=dont-show-again");
    }

    /**
     * This method checks if the domain name from the browser
     * request matches the given pattern.
     *
     * @return the input subsequence matched by the given match
     * else null.
     */
    public String getCleanedUrl() {

        Pattern pattern = Pattern.compile("http.?://[^/]*\\.[^/]*\\.[^/?]*");
        Matcher m = pattern.matcher(this.uri);

        if (m.find()) {
            return m.group();
        }
        return null;
    }
}