package org.zaproxy.addon.attackprevention.utils;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This class accesses the data from the database.
 */
public class DBLoader {

    private static final String DATABASE_PATH = JSONLoader.getLabel("DB_PATH");
    private static final String ABSOLUTE_USER_DIR_PATH = System.getProperty("user.dir");
    private static final String ZAP_EXTENSIONS = "zap-extensions";
    private static final String ZA_PROXY = "zaproxy";
    private static final String HTML_PATH = JSONLoader.getLabel("HTML_PATH");

    private DBLoader() {
        throw new IllegalStateException("Utility class");
    }

    /**
     *
     * This method a set of data from a specific path
     * @param fileName - the name of the file to load
     * @return set of data read from database
     */
    public static String loadDB(String fileName){

        String zapExtPath;

        if (ABSOLUTE_USER_DIR_PATH.contains(ZAP_EXTENSIONS)){
            zapExtPath = ABSOLUTE_USER_DIR_PATH.substring(0,ABSOLUTE_USER_DIR_PATH
                    .indexOf(ZAP_EXTENSIONS));
        }
        else{
            zapExtPath = ABSOLUTE_USER_DIR_PATH.substring(0,ABSOLUTE_USER_DIR_PATH
                    .indexOf(ZA_PROXY));
        }
        if (fileName.contains(".html")) {

            String data = null;

            try {
                data = Files.readString(Path.of(zapExtPath+ HTML_PATH + fileName));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return data;
        }
        String data = null;

        try {
            data = Files.readString(Path.of(zapExtPath+ DATABASE_PATH + fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    /**
     * This method saves a set of String in the specified path
     * @param fileName - the name of the file to write
     * @param data - data to write
     */
    public static void saveToDB(String fileName, String data){

        String zapExtPath;
        if (ABSOLUTE_USER_DIR_PATH.contains(ZAP_EXTENSIONS)){
            zapExtPath = ABSOLUTE_USER_DIR_PATH.substring(0,ABSOLUTE_USER_DIR_PATH
                    .indexOf(ZAP_EXTENSIONS));
        }
        else{
            zapExtPath = ABSOLUTE_USER_DIR_PATH.substring(0,ABSOLUTE_USER_DIR_PATH
                    .indexOf(ZA_PROXY));
        }
        Path dbFile = Path.of(zapExtPath+ DATABASE_PATH +fileName);

        try {
            Files.writeString(dbFile, data);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
