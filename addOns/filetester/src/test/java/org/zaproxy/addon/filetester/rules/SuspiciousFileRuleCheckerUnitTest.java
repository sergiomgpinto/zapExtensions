package org.zaproxy.addon.filetester.rules;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class incorporates unit tests to verify that the SuspiciousFileRuleChecker works as expected.
 */
public class SuspiciousFileRuleCheckerUnitTest {

    static String SUS_FILES_RESOURCES_PATH  = "zap-extensions/addOns/filetester/src/main/resources/org/zaproxy/addon/filetester/resources/suspicious_extensions/";
    static final String ABSOLUTE_USER_DIR_PATH = System.getProperty("user.dir");
    static final String ZAP_EXTENSIONS = "zap-extensions";
    static final String ZA_PROXY = "zaproxy";

    /**
     * This test will check if the rule is able to detect that the test.exe will be labeled as suspicious
     */
    @Test
    void SuspiciousFileRuleCheckerUnitTestOne() {
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
            filePath = zapExtPath + SUS_FILES_RESOURCES_PATH + "test.exe";
        } catch (Exception e) {
            e.printStackTrace();
        }
        SuspiciousFileRuleChecker rulechecker = new SuspiciousFileRuleChecker(filePath);
        assertTrue(rulechecker.checkRule().hasPassed());
    }

    /**
     * This test will check if the rule is able to detect that the test.txt will be labeled as not suspicious
     */
    @Test
    void SuspiciousFileRuleCheckerUnitTestTwo() {
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
            filePath = zapExtPath + SUS_FILES_RESOURCES_PATH + "test.txt";
        } catch (Exception e) {
            e.printStackTrace();
        }
        SuspiciousFileRuleChecker rulechecker = new SuspiciousFileRuleChecker(filePath);
        assertFalse(rulechecker.checkRule().hasPassed());
    }

}