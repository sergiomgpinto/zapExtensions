package org.zaproxy.addon.attackprevention.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class parses the user login credentials for most
 * form types except JSON.
 *
 * @see Parser
 */
public class FormParser extends Parser {

    /**
     * This method parses the username from the request.
     *
     * @return  the user username.
     */
    @Override
    public String parseUsername(String body) {
        String localUsername = null;
        if (body.contains("username")) {
            Pattern pattern = Pattern.compile("username=[^&]*");
            Matcher m = pattern.matcher(body);
            if (m.find())
                localUsername = m.group().substring(9);
        } else if (body.contains("user")) {
            Pattern pattern = Pattern.compile("user=[^&]*");
            Matcher m = pattern.matcher(body);
            if (m.find())
                localUsername = m.group().substring(5);
        } else if (body.contains("uname")) {
            Pattern pattern = Pattern.compile("uname=[^&]*");
            Matcher m = pattern.matcher(body);
            if (m.find())
                localUsername = m.group().substring(6);
        }
        else if (body.contains("identifier")) {
            Pattern pattern = Pattern.compile("identifier=[^&]*");
            Matcher m = pattern.matcher(body);
            if (m.find())
                localUsername = m.group().substring(11);
        }
        else if (body.contains("login")) {
            Pattern pattern = Pattern.compile("login=[^&]*");
            Matcher m = pattern.matcher(body);
            if (m.find())
                localUsername = m.group().substring(6);
        }
        if (localUsername != null && localUsername.contains("%40")) {
            localUsername = localUsername.replace("%40", "@");
        }
        return localUsername;
    }

    /**
     * This method parses the password from the request.
     *
     * @return  the user password.
     */
    @Override
    public String parsePassword(String body) {
        String localPassword = null;
        if (body.contains("password")) {
            Pattern pattern = Pattern.compile("password=[^&]*");
            Matcher m = pattern.matcher(body);
            if (m.find())
                localPassword = m.group().substring(9);
        }
        else if (body.contains("passwd")) {
            Pattern pattern = Pattern.compile("passwd=[^&]*");
            Matcher m = pattern.matcher(body);
            if (m.find())
                localPassword = m.group().substring(7);
        }
        else if (body.contains("pass")) {
            Pattern pattern = Pattern.compile("pass=[^&]*");
            Matcher m = pattern.matcher(body);
            if (m.find())
                localPassword = m.group().substring(5);
        } else if (body.contains("pwd")) {
            Pattern pattern = Pattern.compile("pwd=[^&]*");
            Matcher m = pattern.matcher(body);
            if (m.find())
                localPassword = m.group().substring(4);
        }
        return localPassword;
    }
}
