package org.zaproxy.addon.attackprevention.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.zaproxy.addon.attackprevention.utils.DBLoader;
import org.zaproxy.addon.attackprevention.utils.JSONLoader;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This class incorporates unit tests for
 * the database to verify the correct
 * information is being recorded and
 * persisted after each session.
 */
class DBLoaderUnitTest {

    String originalData = null;
    final String databasePath = JSONLoader.getLabel("DB_PATH");


    /**
     * Sets up the database with some testdata.
     * @throws Exception
     */
    @BeforeEach
    void setUp() throws Exception {

        String absoluteUserDirPath = System.getProperty("user.dir");

        String zapExtPath;
        if (absoluteUserDirPath.contains("zap-extensions")){
            zapExtPath=absoluteUserDirPath.substring(0,absoluteUserDirPath.indexOf("zap-extensions"));
        } else {
            zapExtPath=absoluteUserDirPath.substring(0,absoluteUserDirPath.indexOf("zaproxy"));
        }
        Path dbFile = Path.of(zapExtPath + databasePath + "visited.txt");

        try {
            originalData = Files.readString(dbFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String testData = "www.google.com\nwww.youtube.com";
        Files.writeString(dbFile, testData);
    }

    /**
     * This method verifies that the visited sites are loaded correctly.
     */
    @Test
    void loadVisitedTest(){
        String visited = DBLoader.loadDB("visited.txt");
        assert visited.equals("www.google.com\nwww.youtube.com");
    }

    /**
     * This method verifies that the visited sites are saved (and loaded again) correctly.
     */
    @Test
    void saveVisitedTest(){
        DBLoader.saveToDB("visited.txt","www.facebook.com\nwww.youtube.com");

        String visited = DBLoader.loadDB("visited.txt");
        assert visited.equals("www.facebook.com\nwww.youtube.com");
    }

    /**
     * Restores the original data to database.
     */
    @AfterEach
    void afterTests(){

        String absoluteUserDirPath = System.getProperty("user.dir");

        String zapExtPath;
        if (absoluteUserDirPath.contains("zap-extensions")){
            zapExtPath=absoluteUserDirPath.substring(0, absoluteUserDirPath.indexOf("zap-extensions"));
        } else {
            zapExtPath=absoluteUserDirPath.substring(0, absoluteUserDirPath.indexOf("zaproxy"));
        }
        Path dbFile = Path.of(zapExtPath + databasePath + "visited.txt");

        try {
            Files.writeString(dbFile, originalData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
