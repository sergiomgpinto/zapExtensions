package org.zaproxy.addon.filetester.reporting;

import org.zaproxy.addon.filetester.utils.ResourceLoader;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;


/**
 * This class handles generating a report from a list of test-results.
 */
public class ReportManager {

    Map<String, Set<TestReport>> reports = new HashMap<>(); // key = Filepath

    /**
     * Adds a list of test-reports to the report and returns the test-result.
     * @param reports - the list of reports
     * @return true if at least one of the tests is true (has failed)
     */
    public boolean addReportBatch(List<TestReport> reports) {
        boolean result = false;
        for (TestReport report : reports) {
            addReport(report);
            result |= report.hasPassed();
        }
        writeReport();
        return result;
    }

    /**
     * Adds a single test-report to the report.
     * @param report
     */
    private void addReport(TestReport report) {
        reports.computeIfAbsent(report.getFileName(), k -> new HashSet<>());
        reports.get(report.getFileName()).add(report);
    }

    /**
     * Writes the full report to file.
     * The location is "resources/reports/report.log"
     */
    private void writeReport() {
        StringBuilder builder = new StringBuilder("Generated report:\n");
        for (Map.Entry<String, Set<TestReport>> e : reports.entrySet()) {
            builder.append(e.getKey());
            for (TestReport report : e.getValue()) {
                builder.append("\n\t");
                builder.append(report.getResultString());
            }
            builder.append("\n");
        }

        ResourceLoader.saveString("reports/report.log", builder.toString());
    }

}
