package org.zaproxy.addon.attackprevention.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.zaproxy.addon.attackprevention.utils.JSONLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * This class incorporates all the unit tests for the
 * typoHistory class.
 *
 * @see TyposquattingHistory
 */
class TyposquattingHistoryUnitTest {

    String originalDataVisitedFile;
    String originalDataLegitimateFile;
    final String databasePath = JSONLoader.getLabel("DB_PATH");

    /**
     * Saves backup of the current data.
     */
    @BeforeEach
    void setUp() {

        String absoluteUserDirPath = System.getProperty("user.dir");

        String zapExtPath;
        if (absoluteUserDirPath.contains("zap-extensions")){
            zapExtPath=absoluteUserDirPath.substring(0,absoluteUserDirPath.indexOf("zap-extensions"));
        } else {
            zapExtPath=absoluteUserDirPath.substring(0,absoluteUserDirPath.indexOf("zaproxy"));
        }
        Path dbFileVisited = Path.of(zapExtPath + databasePath + "visited.txt");
        Path dbFileLegitimate = Path.of(zapExtPath + databasePath + "legitimate.txt");

        try {
            originalDataVisitedFile = Files.readString(dbFileVisited);
            originalDataLegitimateFile = Files.readString(dbFileLegitimate);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if returned visited sites collection is not null.
     */
    @Test
    void getVisitedWebsitesTest() {
        TyposquattingHistory typoHistory = new TyposquattingHistory();

        Set<String> visitedWebsites = typoHistory.getVisited();

        assertNotNull(visitedWebsites);
    }

    /**
     * Checks if returned legitimate sites collection is not null.
     */
    @Test
    void getLegitimateWebsitesTest() {
        TyposquattingHistory typoHistory = new TyposquattingHistory();

        Set<String> legitimateWebsites = typoHistory.getLegitimate();

        assertNotNull(legitimateWebsites);
    }

    /**
     * Checks if a legitimate site can be added correctly.
     */
    @Test
    void addLegitimateWebsiteTest() {
        TyposquattingHistory typoHistory = new TyposquattingHistory();

        typoHistory.addLegitimate("www.google.com");

        assertTrue(typoHistory.getLegitimate().contains("www.google.com"));
    }

    /**
     * Checks if a visited site can be added correctly.
     */
    @Test
    void addVisitedWebsiteTest() {
        TyposquattingHistory typoHistory = new TyposquattingHistory();

        typoHistory.addVisited("www.google.com");

        assertTrue(typoHistory.getVisited().contains("www.google.com"));
    }

    /**
     * Checks if a non-legitimate site is indicated as such.
     */
    @Test
    void isLegitimateWhenUrlIsNotLegitimateThenReturnFalseTest() {
        TyposquattingHistory typoHistory = new TyposquattingHistory();

        boolean result = typoHistory.isLegitimate("www.googlexexexe.pl");

        assertFalse(result);
    }

    /**
     * Checks if a legitimate site is indicated as such.
     */
    @Test
    void isLegitimateWhenUrlIsLegitimateThenReturnTrueTest() {
        TyposquattingHistory typoHistory = new TyposquattingHistory();

        typoHistory.addLegitimate("www.url-for-unit-test.com");

        assertTrue(typoHistory.isLegitimate("www.url-for-unit-test.com"));
    }

    /**
     * Checks if a visited site is indicated as such.
     */
    @Test
    void isVisitedWhenUrlIsInVisitedThenReturnTrueTest() {
        TyposquattingHistory typoHistory = new TyposquattingHistory();

        typoHistory.addVisited("www.url-for-unit-test-.com");

        assertTrue(typoHistory.isVisited("www.url-for-unit-test-.com"));
    }

    /**
     * Checks if a non-visited site is indicated as such.
     */
    @Test
    void isVisitedWhenUrlIsNotInVisitedThenReturnFalseTest() {
        TyposquattingHistory typoHistory = new TyposquattingHistory();

        assertFalse(typoHistory.isVisited("www.123kamskf2313.com"));
    }

    /**
     * Restores the original database.
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
        Path dbFileVisited = Path.of(zapExtPath + databasePath + "visited.txt");
        Path dbFileLegitimate = Path.of(zapExtPath + databasePath + "legitimate.txt");

        try {
            Files.writeString(dbFileVisited, originalDataVisitedFile);
            Files.writeString(dbFileLegitimate, originalDataLegitimateFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}