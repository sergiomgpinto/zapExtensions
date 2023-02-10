package org.zaproxy.addon.profilingproxy.improvements.minifier;

/**
 * Contains the options for javascript and css compression.
 */
public class YuiCompressorOptions {

    private final String charset;
    private final int lineBreakPos;
    private final boolean munge;
    private final boolean verbose;
    private final boolean preserveAllSemiColons;
    private final boolean disableOptimizations;

    public YuiCompressorOptions() {
        this.lineBreakPos = -1;
        this.munge = true;
        this.verbose = false;
        this.preserveAllSemiColons = false;
        this.disableOptimizations = false;
        this.charset = "UTF-8";
    }

    public String getCharset() {
        return charset;
    }

    public int getLineBreakPos() {
        return lineBreakPos;
    }

    public boolean isMunge() {
        return munge;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public boolean isPreserveAllSemiColons() {
        return preserveAllSemiColons;
    }

    public boolean isDisableOptimizations() {
        return disableOptimizations;
    }
}
