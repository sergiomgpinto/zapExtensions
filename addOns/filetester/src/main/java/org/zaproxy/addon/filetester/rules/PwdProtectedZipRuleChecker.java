package org.zaproxy.addon.filetester.rules;

import net.lingala.zip4j.ZipFile;
import org.zaproxy.addon.filetester.reporting.TestReport;

/**
 * This class checks if the downloaded zip file is password protected.
 * This class inherits the Rule interface.
 *
 * @see @Rule
 */
public class PwdProtectedZipRuleChecker implements Rule{
    private final String rule = "PwdProtectedZip";
    private final String filePath;
    public PwdProtectedZipRuleChecker(String filePath){
        this.filePath=filePath;
    }
    /**
     * This method checks if the downloaded zip file is password protected.
     *
     * @return TestReport containing if the zip file is password protected
     */
    @Override
    public TestReport checkRule() {
        if (!filePath.endsWith(".zip")) {
            System.out.println("Not a zip file");
            return new TestReport(filePath,  rule,  false, "Not a zip file.");
        }
        try {
            ZipFile zipFile = new ZipFile(filePath);
            if (zipFile.isEncrypted()) {
                return new TestReport(filePath,  rule,  true, "Zip file is password encrypted");
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return  new TestReport(filePath,  rule,  false, "File passed test.");
    }

}
