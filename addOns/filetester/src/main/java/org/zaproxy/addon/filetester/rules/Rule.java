package org.zaproxy.addon.filetester.rules;

import org.zaproxy.addon.filetester.reporting.TestReport;

import java.io.IOException;

/**
 * This interface will define the Rule class and its methods
 */
public interface Rule {
    /**
     * Test if a file passes a rule or not and records the result in a test report
     * @return Test Report containing the results of this test
     * @throws IOException
     * @throws InterruptedException
     */
    TestReport checkRule() throws IOException, InterruptedException;
}
