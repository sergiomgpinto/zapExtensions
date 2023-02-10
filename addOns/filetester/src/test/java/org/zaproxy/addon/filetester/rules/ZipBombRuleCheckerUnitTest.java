package org.zaproxy.addon.filetester.rules;


import org.junit.jupiter.api.Test;
import org.zaproxy.addon.filetester.utils.FileUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * This class incorporates unit tests to verify that the ZipBombRuleChecker works as expected.
 */
public class ZipBombRuleCheckerUnitTest {

    static String ZIP_RESOURCES_PATH  = "/zap-extensions/addOns/filetester/src/main/resources/org/zaproxy/addon/filetester/resources/zip_files/";
    static final String ABSOLUTE_USER_DIR_PATH = System.getProperty("user.dir");
    static final String ZAP_EXTENSIONS = "zap-extensions";
    static final String ZA_PROXY = "zaproxy";

    @Test
    void isSafeZipBombRuleCheckerUnitTest() {
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
        ZipBombRuleChecker rule = new ZipBombRuleChecker(filePath);
        assertFalse(rule.checkRule().hasPassed());
    }

    @Test
    void isLargeFileZipBombRuleCheckerUnitTest() {
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
            filePath = zapExtPath + ZIP_RESOURCES_PATH+"zip_bomb_large_files.zip";
        } catch (Exception e) {
            e.printStackTrace();
        }
        ZipBombRuleChecker rule = new ZipBombRuleChecker(filePath);
        assertTrue(rule.checkRule().hasPassed());
    }

    @Test
    void isGoodFilenameUnitTest() throws IOException {
        String fileName = "good_directory/test.txt";
        String path = "good_directory/";
        ZipBombRuleChecker rule = new ZipBombRuleChecker(fileName);
        String canonicalFilename = FileUtils.validateFilename(fileName, path);
        assertTrue(canonicalFilename!=null);
    }

    @Test
    void isBadFilenameUnitTest() throws IOException {
        String fileName = "bad_directory/test.txt";
        String path = "good_directory/";
        ZipBombRuleChecker rule = new ZipBombRuleChecker(fileName);
        String canonicalFilename = FileUtils.validateFilename(fileName, path);
        assertNull(canonicalFilename);
    }


}