package org.zaproxy.addon.filetester.rules;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class incorporates unit tests to verify that the ExifMetadataRuleChecker works as expected.
 */
public class ExifMetadataRuleCheckerUnitTest {

    static String IMAGES_PATH = "zap-extensions/addOns/filetester/src/main/resources/org/zaproxy/addon/filetester/resources/images/";
    static final String ABSOLUTE_USER_DIR_PATH = System.getProperty("user.dir");
    static final String ZAP_EXTENSIONS = "zap-extensions";
    static final String ZA_PROXY = "zaproxy";

    /**
     * This test will check if the rule is able to detect that the tablet.jpg image contains metadata
     */
    @Test
    void ExifMetadataRuleCheckerUnitTestOne() {
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
            filePath = zapExtPath + IMAGES_PATH + "tablet.jpg";
        } catch (Exception e) {
            e.printStackTrace();
        }
        ExifMetadataRuleChecker rulechecker = new ExifMetadataRuleChecker(filePath);
        assertTrue(rulechecker.checkRule().hasPassed());
    }

    /**
     *  This test will check if the rule is able to detect that the brain.jpg image contains metadata
     */
    @Test
    void ExifMetadataRuleCheckerUnitTestTwo(){
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
            filePath = zapExtPath + IMAGES_PATH + "brain.jpg";
        } catch (Exception e) {
            e.printStackTrace();
        }
        ExifMetadataRuleChecker rulechecker = new ExifMetadataRuleChecker(filePath);
        assertTrue(rulechecker.checkRule().hasPassed());
    }

}