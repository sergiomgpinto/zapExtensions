package org.zaproxy.addon.filetester.files;

import org.zaproxy.addon.filetester.rules.Rule;

import java.util.ArrayList;
import java.util.List;

/**
 * This abstract class defines the type of a file and the rules that this file can be tested for
 */
public abstract class FileType {

    public List<Rule> rules;

    /**
     * Create a FileType object
     */
    public FileType(){
    rules=new ArrayList<>();
}

    /**
     * get a list of all rules for this filetype
     * @return a List of Rules
     */
    public List<Rule> getRules() {
        return rules;
    }

    /**
     * Creates all rules needed to test a specific file
     * @param filepath - Path to the file
     */
    public abstract void createRules(String filepath);
}