package org.zaproxy.addon.filetester.rules;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * This class incorporates unit tests to verify that the VirusTotalRuleChecker works as expected.
 */
public class VirusTotalRuleCheckerUnitTest {

    static String VIRUSTOTAL_RESOURCES_PATH  = "/zap-extensions/addOns/filetester/src/main/resources/org/zaproxy/addon/filetester/resources/virustotal/";
    static final String ABSOLUTE_USER_DIR_PATH = System.getProperty("user.dir");
    static final String ZAP_EXTENSIONS = "zap-extensions";
    static final String ZA_PROXY = "zaproxy";

    /**
     * This test will verify that VirusTotalRuleChecker is able to perform tests on a safe .exe file (full.exe) .
     */
    @Test
    void VirusTotalRuleCheckerUnitTestOne() {
        String zapExtPath;

        if (ABSOLUTE_USER_DIR_PATH.contains(ZAP_EXTENSIONS)){
            zapExtPath = ABSOLUTE_USER_DIR_PATH.substring(0,ABSOLUTE_USER_DIR_PATH
                    .indexOf(ZAP_EXTENSIONS));
        }
        else{
            zapExtPath = ABSOLUTE_USER_DIR_PATH.substring(0,ABSOLUTE_USER_DIR_PATH
                    .indexOf(ZA_PROXY));
        }
        String filePath= null;
        try {
            filePath = zapExtPath + VIRUSTOTAL_RESOURCES_PATH + "full.exe";
        } catch (Exception e) {
            e.printStackTrace();
        }
        VirusTotalRuleChecker rulechecker = new VirusTotalRuleChecker(filePath);
        assertFalse(rulechecker.checkRule().hasPassed());
    }

    /**
     * This test will verify that VirusTotalRuleChecker is able to perform tests on another safe .exe file (7z2201-x64.exe)    */
    @Test
    void VirusTotalRuleCheckerUnitTestTwo() {
        String zapExtPath;

        if (ABSOLUTE_USER_DIR_PATH.contains(ZAP_EXTENSIONS)){
            zapExtPath = ABSOLUTE_USER_DIR_PATH.substring(0,ABSOLUTE_USER_DIR_PATH
                    .indexOf(ZAP_EXTENSIONS));
        }
        else{
            zapExtPath = ABSOLUTE_USER_DIR_PATH.substring(0,ABSOLUTE_USER_DIR_PATH
                    .indexOf(ZA_PROXY));
        }
        String filePath= null;
        try {
            filePath = zapExtPath + VIRUSTOTAL_RESOURCES_PATH + "7z2201-x64.exe";
        } catch (Exception e) {
            e.printStackTrace();
        }
        VirusTotalRuleChecker rulechecker = new VirusTotalRuleChecker(filePath);
        assertFalse(rulechecker.checkRule().hasPassed());
    }

    /**
     * This test will verify that VirusTotalRuleChecker is able to perform tests on a third safe .exe file (nc.exe)
     */
    @Test
    void VirusTotalRuleCheckerUnitTestThree() {
        String zapExtPath;

        if (ABSOLUTE_USER_DIR_PATH.contains(ZAP_EXTENSIONS)){
            zapExtPath = ABSOLUTE_USER_DIR_PATH.substring(0,ABSOLUTE_USER_DIR_PATH
                    .indexOf(ZAP_EXTENSIONS));
        }
        else{
            zapExtPath = ABSOLUTE_USER_DIR_PATH.substring(0,ABSOLUTE_USER_DIR_PATH
                    .indexOf(ZA_PROXY));
        }
        String filePath= null;
        try {
            filePath = zapExtPath + VIRUSTOTAL_RESOURCES_PATH + "nc.exe";
        } catch (Exception e) {
            e.printStackTrace();
        }
        VirusTotalRuleChecker rulechecker = new VirusTotalRuleChecker(filePath);
        assertFalse(rulechecker.checkRule().hasPassed());
    }

}