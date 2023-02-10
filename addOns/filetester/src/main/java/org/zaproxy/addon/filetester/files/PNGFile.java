package org.zaproxy.addon.filetester.files;

import org.zaproxy.addon.filetester.rules.NotValidImageRuleChecker;

/**
 * This class define the Files with the .png extension and the rules related to this filetype
 */
public class PNGFile extends FileType{
    /**
     * Creates all rules needed to test a specific file
     * @param filepath - Path to the file
     */
    @Override
    public void createRules(String filepath) {
        rules.add(new NotValidImageRuleChecker(filepath));
    }
}
