package org.zaproxy.addon.filetester.rules;


import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class incorporates unit tests to verify that the NotValidImageRuleChecker works as expected.
 */
public class NotValidImageRuleCheckerUnitTest {

    static String IMAGES_PATH = "zap-extensions/addOns/filetester/src/main/resources/org/zaproxy/addon/filetester/resources/images/";
    static final String ABSOLUTE_USER_DIR_PATH = System.getProperty("user.dir");
    static final String ZAP_EXTENSIONS = "zap-extensions";
    static final String ZA_PROXY = "zaproxy";

    /**
     *  This test will check if the rule is able to detect that the list_of_extensions.png is not a valid image
     * @throws IOException
     */
    @Test
    void NotValidImageRuleCheckerUnitTestOne() throws IOException {
        String zapExtPath;

        if (ABSOLUTE_USER_DIR_PATH.contains(ZAP_EXTENSIONS)) {
            zapExtPath = ABSOLUTE_USER_DIR_PATH.substring(0, ABSOLUTE_USER_DIR_PATH
                    .indexOf(ZAP_EXTENSIONS));
        } else {
            zapExtPath = ABSOLUTE_USER_DIR_PATH.substring(0, ABSOLUTE_USER_DIR_PATH
                    .indexOf(ZA_PROXY));
        }
        String filePath = null;
        try {
            filePath = zapExtPath + IMAGES_PATH + "list_of_extensions.png";
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(filePath);
        NotValidImageRuleChecker rulechecker = new NotValidImageRuleChecker(filePath);
        assertTrue(rulechecker.checkRule().hasPassed());
    }

    /**
     * This test will check if the rule is able to detect that the tabled.jpg is a valid image
     * @throws IOException
     */
    @Test
    void NotValidImageRuleCheckerUnitTestTwo() throws IOException {
        String zapExtPath;

        if (ABSOLUTE_USER_DIR_PATH.contains(ZAP_EXTENSIONS)) {
            zapExtPath = ABSOLUTE_USER_DIR_PATH.substring(0, ABSOLUTE_USER_DIR_PATH
                    .indexOf(ZAP_EXTENSIONS));
        } else {
            zapExtPath = ABSOLUTE_USER_DIR_PATH.substring(0, ABSOLUTE_USER_DIR_PATH
                    .indexOf(ZA_PROXY));
        }
        String filePath = null;
        try {
            filePath = zapExtPath + IMAGES_PATH + "tablet.jpg";
        } catch (Exception e) {
            e.printStackTrace();
        }
        NotValidImageRuleChecker rulechecker = new NotValidImageRuleChecker(filePath);
        assertFalse(rulechecker.checkRule().hasPassed());
    }
}