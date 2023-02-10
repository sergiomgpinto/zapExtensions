package org.zaproxy.addon.profilingproxy.improvements.minifier;

import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import java.util.logging.Logger;

/**
 * Compresses javascript or css files
 */
public class YuiCompressor {
    private static Logger logger = Logger.getLogger(YuiCompressor.class.getName());

    private YuiCompressor() {
    }

    /**
     * Minifies the given js file, and saves the compressed file
     * @param inputFilename - Name of file to compress
     * @param outputFilename - Name of file after compression
     * @param o - Compressing options
     */
    private static void compressJavaScript(String inputFilename, String outputFilename, YuiCompressorOptions o) throws IOException {
        try (Reader in = new InputStreamReader(new FileInputStream(inputFilename), o.getCharset());
             Writer out = new OutputStreamWriter(new FileOutputStream(outputFilename), o.getCharset())
        ) {
            JavaScriptCompressor compressor = new JavaScriptCompressor(in, new YuiCompressorErrorReporter());
            compressor.compress(out, o.getLineBreakPos(), o.isMunge(), o.isVerbose(), o.isPreserveAllSemiColons(), o.isDisableOptimizations());
        }
    }


    /**
     * Minifies the given css file, and saves the compressed file
     * @param inputFilename - Name of file to compress
     * @param outputFilename - Name of file after compression
     * @param o - Compressing options
     */
    private static void compressCSS(String inputFilename, String outputFilename, YuiCompressorOptions o) throws IOException {
        try (
            Reader in = new InputStreamReader(new FileInputStream(inputFilename), o.getCharset());
            Writer out = new OutputStreamWriter(new FileOutputStream(outputFilename), o.getCharset())
        ) {
            CssCompressor compressor = new CssCompressor(in);
            compressor.compress(out, o.getLineBreakPos());
        }
    }

    /**
     * Minifies the given js or css file, and saves the compressed file
     * @param filetype - Type of file, e.g.: js or css
     * @param inputFilename - Name of file to compress
     * @param outputFilename - Name of file after compression
     * @param yuiCompressorOptions - Compressing yuiCompressorOptions
     */
    public static void compress(String filetype, String inputFilename, String outputFilename, YuiCompressorOptions yuiCompressorOptions) throws IOException {
        if (filetype.equals("js")) {
            compressJavaScript(inputFilename, outputFilename, yuiCompressorOptions);
        } else if (filetype.equals("css")) {
            compressCSS(inputFilename, outputFilename, yuiCompressorOptions);
        }
    }

}

