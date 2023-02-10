package org.zaproxy.addon.attackprevention.database;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Stores functions for conversion of variables.
 *
 */
class Utils {

    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    private static final String ENDLINE = "endline\n";
    private static final String MAP_SEPARATOR = ":mapseperator:";

    /**
     * Deserialise string to Set. Used for storage.
     *
     * @return a Set of Strings
     */
    public static Set<String> stringToSet(String data) {
        if (data == null || data.equals("") || data.equals("[]"))
            return new HashSet<>();

        return new HashSet<>(Set.of(data.substring(1, data.length() - 1).split(", ")));
    }

    /**
     * Deserialise a map from a string.
     */
    static Map<Credentials, Set<String>> stringToMap(String s) {
        Map<Credentials, Set<String>> result = new HashMap<>();

        if (s == null || s.equals(""))
            return result;

        for (String line : s.split(ENDLINE)) {
            String[] lineParts = line.split(MAP_SEPARATOR);
            if (lineParts.length != 2) {
                return result;
            }
            result.put(Credentials.fromString(lineParts[0]), Utils.stringToSet(lineParts[1]));
        }
        return result;
    }

    /**
     * Serialise a map to a string for storage.
     */
    static String mapToString(Map<Credentials, Set<String>> m) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Credentials, Set<String>> entry : m.entrySet()) {
            builder.append(entry.getKey().toString());
            builder.append(MAP_SEPARATOR);
            builder.append(entry.getValue().toString());
            builder.append(ENDLINE);
        }
        return builder.toString();
    }

    /**
     * Deserialize the last visited websites map from a string.
     *
     * @param s - The string to deserialize.
     * @return The serialised map as a string.
     */
    static Map<String, String> stringToLastRequestMap(String s) {
        Map<String, String> result = new HashMap<>();

        if (s == null || s.equals(""))
            return result;

        for (String line : s.split(ENDLINE)) {
            String[] lineParts = line.split(MAP_SEPARATOR);
            if (lineParts.length != 2) {
                return result;
            }
            result.put(lineParts[0], lineParts[1]);
        }

        return result;
    }

    static String lastRequestMapToString(Map<String, String> m) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : m.entrySet()) {
            builder.append(entry.getKey());
            builder.append(MAP_SEPARATOR);
            builder.append(entry.getValue());
            builder.append(ENDLINE);
        }
        return builder.toString();
    }
}
