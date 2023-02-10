package org.zaproxy.addon.profilingproxy.improvements;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.zaproxy.addon.profilingproxy.Message;
import org.zaproxy.addon.profilingproxy.improvements.minifier.YuiCompressorOptions;
import org.zaproxy.addon.profilingproxy.improvements.minifier.YuiCompressor;
import org.zaproxy.addon.profilingproxy.utils.JSONLoader;
import org.zaproxy.addon.profilingproxy.ResourcesLoader;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class checks if the ReduceJSandCSSImprovement class and YuiCompressor class work as intended.
 */
class ReduceJSAndCSSImprovementUnitTest {

    static String JSandCSS_RESOURCES_PATH = JSONLoader.getLabel("jsAndCssResourcesPath");




    /**
     * This method verifies that the calculated size reduction for a javascript file is correct.
     */
    @Test
    void compileJSUnitTest() throws IOException {
        String PATH = ResourcesLoader.getResourcesPath(JSandCSS_RESOURCES_PATH);

        ReduceJSandCSSImprovement compressor = new ReduceJSandCSSImprovement();
        File file = new File(PATH + "js_template.js");
        byte[] fileContent = Files.readAllBytes(file.toPath());
        Message data = new Message(0, 0, 0, null, fileContent,
                false, true, false);
        ImprovementDTO result = compressor.getResult(data);
        assertEquals(51, result.getSizeReduction());
    }

    /**
     * This method verifies that the calculated size reduction for a css file is correct.
     */
    @Test
    void compileCSSUnitTest() throws IOException {
        String PATH = ResourcesLoader.getResourcesPath(JSandCSS_RESOURCES_PATH);

        ReduceJSandCSSImprovement compressor = new ReduceJSandCSSImprovement();
        File file = new File(PATH + "css_template.css");
        byte[] fileContent = Files.readAllBytes(file.toPath());
        Message data = new Message(0, 0, 0, null, fileContent,
                true, false, false);
        ImprovementDTO result = compressor.getResult(data);
        assertEquals(14, result.getSizeReduction());
    }

    /**
     * This method verifies that no compression is done if the given data is not a javascript or css file.
     */
    @Test
    void notCssOrJsFileUnitTest() throws IOException {
        String PATH = ResourcesLoader.getResourcesPath(JSandCSS_RESOURCES_PATH);
        ReduceJSandCSSImprovement compressor = new ReduceJSandCSSImprovement();
        File file = new File(PATH + "css_template.css");
        byte[] fileContent = Files.readAllBytes(file.toPath());
        Message data = new Message(0, 0, 0, null, fileContent,
                false, false, false);
        ImprovementDTO result = compressor.getResult(data);
        assertEquals(0, result.getSizeReduction());
    }

    /**
     * This method verifies that nothing is calculated and compressed when wrong data is given to compress.
     */
    @Test
    void notValidFileUnitTest() throws IOException {
        String PATH = ResourcesLoader.getResourcesPath(JSandCSS_RESOURCES_PATH);
        ReduceJSandCSSImprovement compressor = new ReduceJSandCSSImprovement();
        File file = new File(PATH + "image.png");
        byte[] fileContent = Files.readAllBytes(file.toPath());
        Message data = new Message(0, 0, 0, null, fileContent,
                false, true, false);
        ImprovementDTO result = compressor.getResult(data);
        assertEquals(0, result.getSizeReduction());
    }

    /**
     * This method verifies that the given javascript file is compressed correctly.
     */
    @Test
    void YuiJSCompressorUnitTest() throws IOException {
        String PATH = ResourcesLoader.getResourcesPath(JSandCSS_RESOURCES_PATH);
        YuiCompressorOptions o = new YuiCompressorOptions();
        YuiCompressor.compress("js", PATH+"js_template.js", PATH+"js_out.js", o);
        File file1 = new File(PATH+"js_out.js");
        File file2 = new File(PATH+"js_minified.js");
        boolean isTwoEqual = FileUtils.contentEquals(file1, file2);
        assertTrue(isTwoEqual);
    }

    /**
     * This method verifies that the given css file is compressed correctly.
     */
    @Test
    void YuiCSSCompressorUnitTest() throws IOException {
        String PATH = ResourcesLoader.getResourcesPath(JSandCSS_RESOURCES_PATH);
        YuiCompressorOptions o = new YuiCompressorOptions();
        YuiCompressor.compress("css", PATH+"css_template.css", PATH+"css_out.css", o);
        File file1 = new File(PATH+"css_out.css");
        File file2 = new File(PATH+"css_minified.css");
        boolean isTwoEqual = FileUtils.contentEquals(file1, file2);
        assertTrue(isTwoEqual);
    }

}
