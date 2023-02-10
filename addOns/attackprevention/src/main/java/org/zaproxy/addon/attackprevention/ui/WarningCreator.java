package org.zaproxy.addon.attackprevention.ui;

import org.zaproxy.addon.attackprevention.utils.DBLoader;

/**
 * The WarningCreator interface allows for the creation of new warning pages.
 * The WarningCreator interface provides a method called createWarningPage that
 * creates a new html warning page.
 *
 * @see TypoSquattingWarningCreator
 * @see PhishingWarningCreator
 */
public abstract class WarningCreator {

    /**
     * This method adds the given urls and extra info to a html-template for the warning page.
     *
     * @param args: the extra info to add the to warning page. this can be a url or text
     * @return FinalWarningPageHtml: the html page that should be shown to the user
     */
    abstract String createWarningPage(String... args);

    /**
     * This method loads from the database the html corresponding file
     * to the given template path.
     *
     * @param filePath: Path to the file with the html template.
     * @return FinalWarningPageHtml: the html page that should be shown to the user
     */
    String retrieveHtmlFileFromDatabase(String filePath) {
        return DBLoader.loadDB(filePath.substring(filePath.lastIndexOf("/") + 1));
    }
}
