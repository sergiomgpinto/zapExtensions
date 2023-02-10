package org.zaproxy.addon.attackprevention.database;

import org.zaproxy.addon.attackprevention.utils.DBLoader;
import org.zaproxy.addon.attackprevention.utils.JSONLoader;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class retrieves from the database if the password
 * hygiene mechanism is enabled or not.
 */
public class PasswordHygieneHistory {

    final String passwordHygieneMechanismFile = JSONLoader.getLabel("PasswordHygieneMechanismFile");

    final String userPasswordWarningPreferencesFile = JSONLoader.getLabel("UserPasswordWarningPreferencesFile");
    Map<String, Set<String>> userPasswordWarningPreferences;
    public PasswordHygieneHistory() {
        userPasswordWarningPreferences = stringToMapWarningPreference(
                DBLoader.loadDB(userPasswordWarningPreferencesFile));
    }

    /**
     * @return true if password Hygiene Mechanism enabled else false
     */
    public boolean isMechanismEnabled() {
        return Boolean.parseBoolean(DBLoader
                .loadDB(passwordHygieneMechanismFile));
    }

    /**
     * Set the user password warning preference for the given website.
     *
     * @param username - The user username.
     * @param website - The website the user logged in.
     */
    public void saveWarningPreference(String username, String website) {

        if (userPasswordWarningPreferences.containsKey(username)) {
            userPasswordWarningPreferences.get(username).add(website);
        } else {
            userPasswordWarningPreferences.put(username, new HashSet<>(Set.of(website)));
        }

        DBLoader.saveToDB(userPasswordWarningPreferencesFile, mapToStringWarningPreference
                (userPasswordWarningPreferences));
    }
    /**
     * Get the user password warning preference for the given website.
     *
     * @param username - The user username.
     * @param website - The website the user logged in.
     * @return true if the user has set the preference to not show the warning, false otherwise.
     */
    public boolean getWarningPreference(String username, String website) {
        if (userPasswordWarningPreferences.containsKey(username)) {
            return userPasswordWarningPreferences.get(username).contains(website);
        }
        return false;
    }

    /**
     * Serialise a map to a string for the users password warning preferences.
     * @param m - The map to be serialised.
     * @return The serialised map.
     */
    private String mapToStringWarningPreference(Map<String, Set<String>> m) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Set<String>> entry : m.entrySet()) {
            builder.append(entry.getKey());
            builder.append(":mapseperator:");
            builder.append(entry.getValue().toString());
            builder.append("endline\n");
        }

        return builder.toString();
    }

    /**
     * Deserialize a map from a string for the users password warning preferences.
     * @param s - The string to be deserialized.
     * @return The deserialized map.
     */
    private Map<String, Set<String>> stringToMapWarningPreference(String s) {
        Map<String, Set<String>> result = new HashMap<>();

        if (s == null || s.equals(""))
            return result;

        for (String line : s.split("endline\n")) {
            String[] lineParts = line.split(":mapseperator:");
            if (lineParts.length != 2) {
                return result;
            }
            result.put(lineParts[0], Utils.stringToSet(lineParts[1]));
        }
        return result;
    }
}
