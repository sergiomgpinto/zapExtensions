package org.zaproxy.addon.attackprevention.ui;

import org.zaproxy.addon.attackprevention.utils.JSONLoader;

/**
 * This class generates a warning page that lets the user know
 * that the website he intends to visit might encompass a
 * phishing attack or that his passwork is weak. The
 * warning page shows a descriptive warning message
 * where the user is given options. The user can
 * also choose to store his preference.
 */
public class PhishingWarningCreator extends WarningCreator {

    private final String PhishingWarningCreatorPathPassword = JSONLoader.getLabel("PhishingWarningCreatorPathPassword");
    private final String PhishingWarningCreatorPathLogin = JSONLoader.getLabel("PhishingWarningCreatorPathLogin");

    /**
     * This method adds the correct links to a html-template for the warning page.
     *
     * @param args - args[0] = Flag that chooses the correct warning page.
     *               args[1] = The link to follow if the user clicks "Cancel login"
     *               args[2] = The link to follow if the user clicks "I still want to continue"
     * @return FinalWarningPageHtml: the html page that should be shown to the user
     */
    @Override
    public String createWarningPage(String... args) {

        if (args[0].equals("PasswordWarning")) {

            String continueUrl = args[1];
            String reason = args[2];

            return createPasswordWarningPage(PhishingWarningCreatorPathPassword, continueUrl, reason);
        }
        else if (args[0].equals("LoginWarning")) {

            String goBackURL = args[1];
            String continueURL = args[2];

            return createLoginWarningPage(PhishingWarningCreatorPathLogin, goBackURL, continueURL);
        }
        return null;
    }

    /**
     * This method adds the correct links to a html-template for the Login warning page.
     *
     * @param templatePath - The path to the html-template.
     * @param goBackURL - The link to follow if the user clicks "Cancel login".
     * @param continueURL - The link to follow if the user clicks "I still want to continue".
     * @return FinalWarningPageHtml: the html page that should be shown to the user
     */
    private String createLoginWarningPage(String templatePath, String goBackURL, String continueURL) {

        String warningPageHtml = retrieveHtmlFileFromDatabase(templatePath);

        String updatedWarningPageHtml = warningPageHtml.replace("GOBACK", goBackURL);
        return updatedWarningPageHtml.replace("CONTINUE", continueURL);
    }

    /**
     * This method adds the correct links to a html-template for the Password warning page.
     * This method also adds the reason why the password is considered unsafe.
     *
     * @param templatePath - The path to the html-template.
     * @param continueURL - The link to follow if the user clicks "I want to continue".
     * @param reason - The reason why the password is considered unsafe.
     * @return FinalWarningPageHtml: the html page that should be shown to the user
     */
    private String createPasswordWarningPage(String templatePath, String continueURL, String reason) {

        String warningPageHtml = retrieveHtmlFileFromDatabase(templatePath);

        String tempWarningHtml = warningPageHtml.replace("REASON", reason);
        return tempWarningHtml.replace("CONTINUE",continueURL);
    }
}
