package org.zaproxy.addon.profilingproxy.improvements;

import org.zaproxy.addon.profilingproxy.Message;
import org.zaproxy.addon.profilingproxy.improvements.minifier.YuiCompressorOptions;
import org.zaproxy.addon.profilingproxy.improvements.minifier.YuiCompressor;
import org.zaproxy.addon.profilingproxy.utils.JSONLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Calculates the size reduction in bytes after Javascript and CSS compression.
 */
public class ReduceJSandCSSImprovement implements Improvement {
    private final String PATH;

    public ReduceJSandCSSImprovement() {
        String ZIP_RESOURCES_PATH  = JSONLoader.getLabel("jsAndCssResourcesPath");
        String ABSOLUTE_USER_DIR_PATH = System.getProperty("user.dir");
        String ZAP_EXTENSIONS = "zap-extensions";
        String ZA_PROXY = "zaproxy";
        String zapExtPath;

        if (ABSOLUTE_USER_DIR_PATH.contains(ZAP_EXTENSIONS)){
            zapExtPath = ABSOLUTE_USER_DIR_PATH.substring(0,ABSOLUTE_USER_DIR_PATH
                    .indexOf(ZAP_EXTENSIONS));
        }
        else{
            zapExtPath = ABSOLUTE_USER_DIR_PATH.substring(0,ABSOLUTE_USER_DIR_PATH
                    .indexOf(ZA_PROXY));
        }
        PATH = zapExtPath + ZIP_RESOURCES_PATH;

    }

    /**
     * Calculates the size reduction in bytes after Javascript and CSS compression
     * @param data - Message that will be checked for JS and CSS compression
     * @return Size reduction in bytes after Javascript and CSS compression
     */
    @Override
    public ImprovementDTO getResult(Message data) {

        YuiCompressorOptions o = new YuiCompressorOptions();
        int sizeReduction;
        String inputFilename;
        String outputFilename;
        String filetype;

        ImprovementDTO improvementDTO = new ImprovementDTO(false,
                true,
                false);

        if (data.isJS()) {
            inputFilename = PATH + "js_in.js";
            outputFilename = PATH + "js_out.js";
            filetype = "js";
            improvementDTO.setReduceJSFile(true);
        } else if (data.isCSS()) {
            inputFilename = PATH + "css_in.css";
            outputFilename = PATH + "css_out.css";
            filetype = "css";
            improvementDTO.setReduceCSSFile(true);
        } else {
            improvementDTO.setImprovementIsPossible(false);
            return improvementDTO;
        }

        try (FileOutputStream stream = new FileOutputStream(inputFilename)) {
            stream.write(data.getResponseBody());
            YuiCompressor.compress(filetype, inputFilename, outputFilename, o);
            sizeReduction = getSizeReduction(inputFilename, outputFilename);

            if (sizeReduction > 0) {
                improvementDTO.setImprovementIsPossible(true);
                improvementDTO.setSizeReduction(sizeReduction);
                improvementDTO.setUrl(data.getUrl());
            } else {
                improvementDTO.setImprovementIsPossible(false);
            }
        } catch (Exception e ) {
            improvementDTO.setImprovementIsPossible(false);
            return improvementDTO;
        }
        return improvementDTO;
    }

    /**
     * Calculates the size difference in bytes between the two given files
     * @param filename - Name of file to compress
     * @param compressedFilename - Name of compressed file
     * @return Difference in bytes between the given files
     */
    private int getSizeReduction(String filename, String compressedFilename) throws IOException {
        long sizeFile = Files.size((new File(filename)).toPath());
        long sizeCompressedFile = Files.size((new File(compressedFilename)).toPath());
        return (int) (sizeFile-sizeCompressedFile);
    }

}


