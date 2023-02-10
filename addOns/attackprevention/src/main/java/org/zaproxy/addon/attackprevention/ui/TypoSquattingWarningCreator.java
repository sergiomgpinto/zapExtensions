package org.zaproxy.addon.attackprevention.ui;

import org.zaproxy.addon.attackprevention.utils.JSONLoader;

/**
 * This class generates a warning page that let the user know
 * the website he intends to visit might be a typosquatting attack.
 * The warning page shows a descriptive warning message and gives the
 * user the possibility to proceed to the site they entered or a
 * recommended safe alternative. The user can also choose to
 * store his preference.
 */
public class TypoSquattingWarningCreator extends WarningCreator {

    private final String TyposquattingWarningCreatorPath = JSONLoader.getLabel("TyposquattingWarningCreatorPath");
    private final String warningPageHtml = retrieveHtmlFileFromDatabase(TyposquattingWarningCreatorPath);

    /**
     * This method adds the suspicious url and recommended url to a html-template for the warning page.
     *
     * @param args - args[0] -> suspiciousURL - website that user entered which might be
     *             a typosquatting attack.
     *               args[1] -> recommendedURL - a safe alternative for the suspiciousURL.
     * @return FinalWarningPageHtml: the html page that should be shown to the user
     */
    @Override
    public String createWarningPage(String... args) {

        String suspiciousURL = args[0];
        String recommendedURL = args[1];

        String updatedWarningPageHtml = warningPageHtml.replace("SUSPICIOUSSITE", suspiciousURL);
        return updatedWarningPageHtml.replace("RECOMMENDEDSITE", recommendedURL);
    }
}