package org.zaproxy.addon.filetester.utils;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This class accesses the data from the database.
 */
public class JSONLoader {
    static final String JSON_PATH = "/zap-extensions/addOns/filetester/src" +
            "/main/resources/org/zaproxy/addon/filetester/resources/properties.json";

    static JSONObject properties=null;
    private JSONLoader() {
        throw new IllegalStateException("Utility class");
    }

    /**
     *
     * This method gets the value of a specific attribute from the jsonString
     * @param jsonString - the json string to read
     * @param attrName - the name of the attribute
     * @return attribute value
     */
    public static Object getAttributeFromJson(String jsonString, String attrName) {



        JSONObject properties = null;
        try {
            properties = toJson(jsonString);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties.get(attrName);
    }

    /**
     *
     * This method gets the value of a specific attribute from the properties files
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

    /**
     * This method turns a string into a json object
     * @param s - the string to convert
     * @return attribute value
     */
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
