package org.zaproxy.addon.filetester.utils;

import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * This class manages access to the resources folder.
 */
public class ResourceLoader {
    static final String RESOURCES_PATH = "/zap-extensions/addOns/filetester/src/main/resources/org/zaproxy/addon/filetester/resources/";
    static final String BASE_PATH;
    static final String ZAP_EXTENSIONS_FOLDER = "zap-extensions";
    static final String ZAPROXY_FOLDER = "zaproxy";


    static {
        String absUserDirPath = System.getProperty("user.dir");
        if (absUserDirPath.contains(ZAP_EXTENSIONS_FOLDER))
            BASE_PATH = absUserDirPath.substring(0,absUserDirPath.indexOf(ZAP_EXTENSIONS_FOLDER));
        else
            BASE_PATH = absUserDirPath.substring(0,absUserDirPath.indexOf(ZAPROXY_FOLDER));
    }


    /**
     * Get the base path of the resource folder. Useful for libraries needing a full path.
     * @return the path to the resources folder
     */
    public static String getResourcesPath() {
        return BASE_PATH + RESOURCES_PATH;
    }


    /**
     * Load a file as a string.
     * @param fileName - the name of the file to load
     */
    public static String loadString(String fileName){
        String data = null;

        try {
            data = Files.readString(Path.of(BASE_PATH + RESOURCES_PATH + fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    /**
     * Save a string to a file.
     * @param fileName - the name of the file to write
     * @param data - the string to write
     */
    public static void saveString(String fileName, String data){
        Path dbFile = Path.of(BASE_PATH + RESOURCES_PATH + fileName);

        try {
            Files.writeString(dbFile, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Load a file as a byte array.
     * @param fileName - the name of the file to load
     */
    public static byte[] loadBytes(String fileName){
        byte[] data = null;

        try {
            data = Files.readAllBytes(Path.of(BASE_PATH + RESOURCES_PATH + fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    /**
     * Save a file as a byte array.
     * @param fileName - the name of the file to write
     * @param data - the data to write
     */
    public static void saveBytes(String fileName, byte[] data){
        Path dbFile = Path.of(BASE_PATH + RESOURCES_PATH + fileName);

        OpenOption[] options = new OpenOption[] { StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING };
        try {
            Files.write(dbFile, data, options);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
