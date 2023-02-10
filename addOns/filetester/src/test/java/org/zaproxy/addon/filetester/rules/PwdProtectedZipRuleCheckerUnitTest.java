package org.zaproxy.addon.filetester.rules;


import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class incorporates unit tests to verify that the PwdProtectedZipRuleChecker works as expected.
 */
public class PwdProtectedZipRuleCheckerUnitTest {

    static String ZIP_RESOURCES_PATH  = "/zap-extensions/addOns/filetester/src/main/resources/org/zaproxy/addon/filetester/resources/zip_files/";
    static final String ABSOLUTE_USER_DIR_PATH = System.getProperty("user.dir");
    static final String ZAP_EXTENSIONS = "zap-extensions";
    static final String ZA_PROXY = "zaproxy";

    @Test
    void isPwdProtectedZipRuleCheckerUnitTest() {
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
            filePath = zapExtPath + ZIP_RESOURCES_PATH+"pwd_protected_zip.zip";
        } catch (Exception e) {
            e.printStackTrace();
        }
        PwdProtectedZipRuleChecker rule = new PwdProtectedZipRuleChecker(filePath);
        assertTrue(rule.checkRule().hasPassed());
    }


    @Test
    void isNotPwdProtectedZipRuleCheckerUnitTest() {
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
            filePath = zapExtPath + ZIP_RESOURCES_PATH+"normal_zip.zip";
        } catch (Exception e) {
            e.printStackTrace();
        }
        PwdProtectedZipRuleChecker rule = new PwdProtectedZipRuleChecker(filePath);
        assertFalse(rule.checkRule().hasPassed());
    }

}