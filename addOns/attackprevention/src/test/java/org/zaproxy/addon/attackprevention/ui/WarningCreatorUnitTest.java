package org.zaproxy.addon.attackprevention.ui;

import org.junit.jupiter.api.Test;

/**
 * This class incorporates unit tests to verify
 * that warning pages are correctly created.
 */
class WarningCreatorUnitTest {

    /**
     * This method tests that all the given urls are added to the warning page template.
     */
    @Test
    void TypoSquattingWarningPageTest() {
        TypoSquattingWarningCreator warningCreator = new TypoSquattingWarningCreator();
        String knownURL = "https://www.google.com";
        String susURL = "https//www.goggle.com";
        String warningpage = warningCreator.createWarningPage(knownURL,susURL);
        assert warningpage.contains(knownURL);
        assert warningpage.contains(susURL);
    }

    /**
     * This method tests that all the given urls are added to the warning page template.
     */
    @Test
    void LoginWarningPageTest() {
        PhishingWarningCreator warningCreator = new PhishingWarningCreator();
        String goBackURL = "https://www.google.com";
        String continueURL = "https//www.goggle.com";
        String warningpage = warningCreator.createWarningPage("LoginWarning",goBackURL,continueURL);
        assert (warningpage.contains(goBackURL));
        assert (warningpage.contains(continueURL));
    }

    /**
     * This method tests that all the given urls and the reason why the password is unsafe
     * are added to the warning page template.
     */
    @Test
    void PasswordWarningPageTest() {
        PhishingWarningCreator warningCreator = new PhishingWarningCreator();

        String continueURL = "https//www.goggle.com";
        String reason = "Your password only contains numbers";

        String warningpage =  warningCreator.createWarningPage("PasswordWarning", continueURL, reason);
        assert (warningpage.contains(continueURL));
        assert (warningpage.contains(reason));
    }
}