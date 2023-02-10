package org.zaproxy.addon.profilingproxy.improvements;

import org.junit.jupiter.api.Test;
import org.zaproxy.addon.profilingproxy.Message;
import org.zaproxy.addon.profilingproxy.utils.JSONLoader;
import org.zaproxy.addon.profilingproxy.ResourcesLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * This class checks if the ReducePNGImprovement class works as intended.
 */
class ReducePNGImprovementUnitTest {

    static String PNG_RESOURCES_PATH = JSONLoader.getLabel("pngResourcesPath");



    /**
     * This method verifies that the calculated size reduction for a png file is correct.
     */
    @Test
    void pngSizeTest() throws IOException {
        String PATH = ResourcesLoader.getResourcesPath(PNG_RESOURCES_PATH);
        ReducePNGImprovement improvement = new ReducePNGImprovement();

        File file1 = new File(PATH + "doctor_50p_chance_die_make_100p.png");
        File file2 = new File(PATH + "open_window_hot_space_station.png");
        File file3 = new File(PATH + "stalin_who_is_it_necessary_food.png");

        Message data1 = new Message(0, 0, 0, null,
                Files.readAllBytes(file1.toPath()),false, false, true);
        Message data2 = new Message(0, 0, 0, null,
                Files.readAllBytes(file2.toPath()),false, false, true);
        Message data3 = new Message(0, 0, 0, null,
                Files.readAllBytes(file3.toPath()), false, false, true);
        ImprovementDTO result1 = improvement.getResult(data1);
        ImprovementDTO result2 = improvement.getResult(data2);
        ImprovementDTO result3 = improvement.getResult(data3);

        assertEquals(169327, result1.getSizeReduction());
        assertEquals(307027, result2.getSizeReduction());
        assertEquals(420402, result3.getSizeReduction());
    }

    /**
     * This method verifies that no compression is done if the given data is not a png file.
     */
    @Test
    void notPngFileTest() throws IOException {
        String PATH = ResourcesLoader.getResourcesPath(PNG_RESOURCES_PATH);
        ReducePNGImprovement compressor = new ReducePNGImprovement();

        File file = new File(PATH + "text_file.txt");
        Message data = new Message(0, 0, 0, null,
                Files.readAllBytes(file.toPath()),false, false, false);
        ImprovementDTO result = compressor.getResult(data);
        assertFalse(result.isImprovementIsPossible());
    }
}
