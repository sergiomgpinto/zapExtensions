package org.zaproxy.addon.filetester.reporting;

import java.io.File;

/**
 * A class to record the results of a test.
 */
public class TestReport {


    private final String fileName;
    private final String ruleName;
    private final boolean result;
    private final String info;

    /**
     *  Create the test report to be written in the log file
     * @param fileName - Name of the downloaded File
     * @param ruleName - The rule checked
     * @param result - The result of checking this rule (true if rule was passed/false otherwise)
     * @param info - Extra Information to be added about the result of the rule
     */
    public TestReport(String fileName, String ruleName, boolean result, String info) {
        this.fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
        this.ruleName = ruleName;
        this.result = result;
        this.info = info;
    }

    /**
     * Get the name of the downloaded file
     * @return file name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Return the result of the rule
     * @return true if rule was passed/false otherwise
     */
    public boolean hasPassed() {
        return result;
    }


    /**
     * Specifies how a report will be printed.
     * @return the string to put in the report
     */
    public String getResultString() {
        String s = "rule: " + ruleName + "; " +
                "result: " + result + "; " +
                "info: " + info + "; ";
        return s;
    }

}
