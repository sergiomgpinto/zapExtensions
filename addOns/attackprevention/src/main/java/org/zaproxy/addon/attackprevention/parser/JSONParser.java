package org.zaproxy.addon.attackprevention.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSONParser extends Parser {

    /**
     * This method parses the password from the request with a body not
     * in a JSON format.
     *
     * @param body - the request body.
     * @return  the user password.
     */
    @Override
    public String parsePassword(String body) {
        String localPassword = null;
        Pattern pattern = Pattern.compile("\"password\"\\s*:\\s*\"[^\"]*\"");
        Matcher m = pattern.matcher(body);

        if (m.find()) {
            localPassword = m.group().substring(12);
            localPassword = localPassword.substring(0, localPassword.length() - 1);
        }
        return localPassword;
    }

    /**
     * This method parses the username from the request with a body not
     * in a JSON format.
     *
     * @param body - the request body.
     * @return  the user username
     */
    @Override
    public String parseUsername(String body) {
        String localUsername = null;
        if (body.contains("email")) {
            Pattern pattern = Pattern.compile("\"email\"\\s*:\\s*\"[^\"]*\"");
            Matcher m = pattern.matcher(body);
            if (m.find()) {
                localUsername = m.group().substring(9);
                localUsername = localUsername.substring(0, localUsername.length() - 1);
            }
        }
        else if (body.contains("login")) {
            Pattern pattern = Pattern.compile("\"login\"\\s*:\\s*\"[^\"]*\"");
            Matcher m = pattern.matcher(body);
            if (m.find()) {
                localUsername = m.group().substring(9);
                localUsername = localUsername.substring(0, localUsername.length() - 1);
            }
        }
        return localUsername;
    }
}
