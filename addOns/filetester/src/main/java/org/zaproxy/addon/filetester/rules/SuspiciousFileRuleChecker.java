package org.zaproxy.addon.filetester.rules;

import org.zaproxy.addon.filetester.utils.ResourceLoader;
import org.zaproxy.addon.filetester.reporting.TestReport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class will check if a file has a suspicious extension based on a database of suspicious extensions
 * This class inherits the Rule interface.
 *
 * @see @Rule
 */
public class SuspiciousFileRuleChecker implements Rule {
    private final String filePath;
    private final String SUS_FILES_EXT_PATH = "suspicious_extensions/list_of_extensions.csv";

    private final String rule="SuspiciousFileRuleChecker";
    public SuspiciousFileRuleChecker(String filePath) {
        this.filePath = filePath;
    }

    /**
     * This method will check if a file has a suspicious extension or not
     *
     * @return TestReport containing if it has a suspicious extension and False otherwise
     */
    @Override
    public TestReport checkRule() {
        String extensions = ResourceLoader.loadString(SUS_FILES_EXT_PATH);
        List<String> myList = new ArrayList<>(Arrays.asList(extensions.replaceAll("\\s+", " ").split(" ")));
        String extension = filePath.substring(filePath.indexOf('.') + 1);
        boolean result=myList.contains(extension);
        if(result){
            return new TestReport(filePath,rule,result,"Suspicious Extension detected");
        }
        else{
       return new TestReport(filePath,rule,result,"No Suspicious Extension detected");
        }
    }
}
