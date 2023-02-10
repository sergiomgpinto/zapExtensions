package org.zaproxy.addon.attackprevention.database;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 * This class tests the PhishingHistory and its persistence.
 */
class PhishingHistoryUnitTest {

    /**
     * This method verifies the correct behaviour of the PhishingHistory class when adding and removing information to the database.
     */
    @Test
    void testPhishingHistory() {
        PhishingHistory hist = new PhishingHistory();

        assert !hist.hasCredsUsedByOtherSites(null, "https://www.google.com");
        assert !hist.isLegitimateSite(null, "https://www.google.com");
        assert !hist.hasCredsUsedByOtherSites(null, null);
        assert !hist.isLegitimateSite(null, null);

        Credentials c1 = hist.getCredential("tim", "passwd_tim");
        Credentials c2 = hist.getCredential("tom", "passwd_tom");
        Credentials c3 = hist.getCredential("jonas", "paswoooooord");

        hist.addVisitedSite("tim", "passwd_tim", "https://www.google.com");
        hist.addVisitedSite("tim", "passwd_tim", "https://www.facebook.com");
        hist.addVisitedSite("jonas", "paswoooooord", "https://www.google.com");
        hist.addVisitedSite("jonas", "paswoooooord", "https://www.github.com");

        hist.addLegitimateSite(c1,"https://www.legitsite.com");
        hist.addLegitimateSite(c1,"https://www.alsolegitsite.com");
        hist.addLegitimateSite(c3,"https://www.legitsite.com");
        hist.addLegitimateSite(c3,"https://www.otherlegitsite.com");

        runAsserts(hist, c1, c2, c3);
        hist = new PhishingHistory();
        runAsserts(hist, c1, c2, c3);

    }


    /**
     * Removes the added visited sites from the history.
     */
    @AfterEach
    void cleanUp() {
        PhishingHistoryTesting phishingHistory = new PhishingHistoryTesting();
        phishingHistory.removeVisitedSite("tim", "passwd_tim", "https://www.google.com");
        phishingHistory.removeVisitedSite("tim", "passwd_tim", "https://www.facebook.com");
        phishingHistory.removeVisitedSite("jonas", "paswoooooord", "https://www.google.com");
        phishingHistory.removeVisitedSite("jonas", "paswoooooord", "https://www.github.com");
    }

    /**
     * Clears the phishing database.
     */
    @AfterAll
    static void cleanUpAll() {
        PhishingHistoryTesting phishingHistory = new PhishingHistoryTesting();
        phishingHistory.cleanVisitedSiteandLegitimateSite();
    }

    /**
     * Runs the main assert statements for a collection of credentials.
     * It tests whether the history correctly reports when a Credential
     * has been used on other sites before and whether legitimate sites
     * are reported correctly.
     *
     * @param hist - the history to test
     * @param c1 - first set of credentials
     * @param c2 - second set of credentials
     * @param c3 - third set of credentials
     */
    private void runAsserts(PhishingHistory hist, Credentials c1, Credentials c2, Credentials c3) {
        assert hist.hasCredsUsedByOtherSites(c1, "https://www.google.com");
        assert hist.hasCredsUsedByOtherSites(c1, "https://www.facebook.com");
        assert hist.hasCredsUsedByOtherSites(c1, "https://www.github.com");

        assert hist.hasCredsUsedByOtherSites(c3, "https://www.google.com");
        assert hist.hasCredsUsedByOtherSites(c3, "https://www.github.com");

        assert !hist.hasCredsUsedByOtherSites(c2, "https://www.google.com");
        assert !hist.hasCredsUsedByOtherSites(c2, "https://www.facebook.com");

        assert hist.isLegitimateSite(c1, "https://www.legitsite.com");
        assert hist.isLegitimateSite(c1, "https://www.alsolegitsite.com");
        assert hist.isLegitimateSite(c3, "https://www.legitsite.com");
        assert hist.isLegitimateSite(c3, "https://www.otherlegitsite.com");
        assert !hist.isLegitimateSite(c2, "https://www.legitsite.com");
    }
}
