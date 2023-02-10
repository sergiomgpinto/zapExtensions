package org.zaproxy.addon.profilingproxy.improvements;

import org.junit.jupiter.api.Test;
import org.zaproxy.addon.profilingproxy.Message;
import org.zaproxy.addon.profilingproxy.utils.JSONLoader;
import org.zaproxy.addon.profilingproxy.ResourcesLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class checks if the CachingImprovement class works as intended.
 */
class CachingImprovementUnitTest {

    static String PNG_RESOURCES_PATH = JSONLoader.getLabel("pngResourcesPath");


    /**
     * This method verifies the case when the pair url-content is not in the cache, when it is, when we
     * add another pair to the cache and when we add a pair to the cache with a known url but new
     * content.
     */
    @Test
    void testAllCasesForCachingImprovementTest() throws IOException {
        String PATH = ResourcesLoader.getResourcesPath(PNG_RESOURCES_PATH);

        CachingImprovement improvement = new CachingImprovement();

        File file1 = new File(PATH + "doctor_50p_chance_die_make_100p.png");
        File file2 = new File(PATH + "open_window_hot_space_station.png");

        String url1 = "www.google.com";
        String url2 = "www.yahoo.com";

        Message data1 = new Message(0, 0, 0, url1,
                Files.readAllBytes(file1.toPath()),false, false, true);
        Message data2 = new Message(0, 0, 0, url1,
                Files.readAllBytes(file1.toPath()),false, false, true);
        Message data3 = new Message(0, 0, 0, url1,
                Files.readAllBytes(file2.toPath()),false, false, true);
        Message data4 = new Message(0, 0, 0, url2,
                Files.readAllBytes(file1.toPath()),false, false, true);

        ImprovementDTO result1 = improvement.getResult(data1);
        ImprovementDTO result2 = improvement.getResult(data2);
        ImprovementDTO result3 = improvement.getResult(data3);
        ImprovementDTO result4 = improvement.getResult(data4);

        assertFalse(result1.isImprovementIsPossible());
        assertTrue(result2.isImprovementIsPossible());
        assertFalse(result3.isImprovementIsPossible());
        assertFalse(result4.isImprovementIsPossible());
    }
}
