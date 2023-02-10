package org.zaproxy.addon.filetester.rules;


import org.zaproxy.addon.filetester.reporting.TestReport;
import org.zaproxy.addon.filetester.utils.VirusTotalAPIHandler;

/**
 * This class checks if the downloaded file is safe according to Virus Total.
 * This class inherits the Rule interface.
 *
 * @see @Rule
 */
public class VirusTotalRuleChecker implements Rule{

    private final String filePath;
    private String result = null;
    private final String rule="VirusTotalRuleChecker";
    public VirusTotalRuleChecker(String filePath){
        this.filePath=filePath;
    }

    /**
     * This method checks if the downloaded file is safe according to Virus Total.
     *
     * @return TestReport containing if the file is dangerous
     */
    @Override
    public TestReport checkRule() {
        //filePath = "C:\\Users\\lodev\\Downloads\\_temp_matlab_R2020a_win64\\sys\\java\\jre\\win64\\jre\\lib\\ext\\access-bridge-64";
        String scanInfo = null;
        try {
            scanInfo = VirusTotalAPIHandler.scan(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Integer positives = null;
        String report = null;
        while (positives == null) {
            try {
                report = VirusTotalAPIHandler.report(VirusTotalAPIHandler.getResourceIdFromScanInfo(scanInfo));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                positives = VirusTotalAPIHandler.checkReportForPositives(report);
            } catch (Exception e) {
                // sleep for 1000 milleseconds = 1 second before trying again
                try {
                    Thread.sleep(1000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        // only make this.result equal to report after leaving the while-loop  because the file might be waiting
        // in a queue to be tested by Virus Total
        this.result = report;
        if (positives > 0) {
            return new TestReport(filePath,  rule,  true, result);
        } else {
            return new TestReport(filePath,  rule,  false, "File scanned by Virus Total is safe");
        }
    }

}










