package org.zaproxy.addon.attackprevention.utils;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This class accesses the paths and specific labels from the database.
 */
public class JSONLoader {

    static final String JSON_PATH = "/zap-extensions/addOns/attackprevention/src" +
            "/main/resources/org/zaproxy/addon/attackprevention/resources/properties.json";

    static JSONObject properties=null;
    private JSONLoader() {
        throw new IllegalStateException("Utility class");
    }

    /**
     *
     * This method get the value of a specific attribute from the properties files
     * @param attrName - the name of the attribute
     * @return attribute value
     */
    public static String getLabel(String attrName) {
        String absoluteUserDirPath = System.getProperty("user.dir");
        String zapExtPath;
        if (absoluteUserDirPath.contains("zap-extensions")) {
            zapExtPath = absoluteUserDirPath.substring(0, absoluteUserDirPath.indexOf("zap-extensions"));
        } else {
            zapExtPath = absoluteUserDirPath.substring(0, absoluteUserDirPath.indexOf("zaproxy"));
        }

        String data;
        try {
            if (properties==null){
                data = Files.readString(Path.of(zapExtPath + JSON_PATH));
                properties = toJson(data);}

        } catch (Exception e) {
            e.printStackTrace();
        }
        assert properties != null;
        return (String) properties.get(attrName);
    }

    private static JSONObject toJson(String s) throws JSONException {
        JSONObject object;
        s = s.trim();

        if (s.isEmpty()) {
            object = null;
        } else if (s.startsWith("{")) {
            object = JSONObject.fromObject(s);
        } else {
            throw new JSONException("Expected a '{', or an empty message");
        }
        return object;
    }
}
