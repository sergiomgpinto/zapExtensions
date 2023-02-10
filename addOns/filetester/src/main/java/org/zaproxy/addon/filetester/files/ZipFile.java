package org.zaproxy.addon.filetester.files;

import org.zaproxy.addon.filetester.rules.PwdProtectedZipRuleChecker;
import org.zaproxy.addon.filetester.rules.ZipBombRuleChecker;

/**
 * This class define the Files with the .zip extension and the rules related to this filetype
 */
public class ZipFile extends FileType{
    /**
     * Creates all rules needed to test a specific file
     * @param filepath - Path to the file
     */
    @Override
    public void createRules(String filepath) {
        rules.add(new PwdProtectedZipRuleChecker(filepath));
        rules.add(new ZipBombRuleChecker(filepath));

    }
}
