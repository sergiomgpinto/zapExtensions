package org.zaproxy.addon.filetester;

import org.zaproxy.addon.filetester.reporting.TestReport;
import org.zaproxy.addon.filetester.rules.Rule;
import org.zaproxy.addon.filetester.files.FileType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class creates the factory of rules which will be applied
 * to the file downloaded and return the respective test reports.
 */
public class FileTests {

    /**
     *  This method performs the tests on the downloaded file.
     * @param fileType : the type of downloaded file
     * @param filePath : path to the downloaded file
     * @return list of reports
     */
    public List<TestReport> performTests(FileType fileType,String filePath) {

        fileType.createRules(filePath);
        List<Rule> rulesToCheck = fileType.getRules();

        List<TestReport> reports = new ArrayList<>();

        for (Rule rule : rulesToCheck) {
           try {
                TestReport testReport = rule.checkRule();
                reports.add(testReport);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return reports;
    }
}
