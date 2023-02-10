package org.zaproxy.addon.attackprevention.rules.phishing;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.zaproxy.addon.attackprevention.database.PhishingHistory;
import org.zaproxy.addon.attackprevention.database.PhishingHistoryTesting;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SameCredentialsRuleCheckerUnitTest {

    @BeforeEach
    void setUp() {
        PhishingHistory phishingHistory = new PhishingHistory();

        phishingHistory.addVisitedSite("user-pishingrule-unit-test" ,
                "password-pishingrule-unit-test"
                , "www.google-unit-test.com");
    }

    /**
     * This method tests the case of a phishing attack
     */
    @Test
    void phishingAttackCaseTest() {

        SameCredentialsRuleChecker checker
                = new SameCredentialsRuleChecker("user-pishingrule-unit-test",
                "password-pishingrule-unit-test",
                "www.google-unit-test-test.com");

        assertTrue(checker.checkRule().hasPassed());

        PhishingHistoryTesting phishingHistory = new PhishingHistoryTesting();
        phishingHistory.removeVisitedSite("user-pishingrule-unit-test",
                "password-pishingrule-unit-test",
                "www.google-unit-test-test.com");
    }

    /**
     * This method tests the case where there is no phishing attack and the website is the same
     */
    @Test
    void NoPhishingAttackCaseSameWebsiteTest() {
        SameCredentialsRuleChecker checker
                = new SameCredentialsRuleChecker("user-pishingrule-unit-test" ,
                "password-pishingrule-unit-test"
                , "www.google-unit-test.com");

        assertFalse(checker.checkRule().hasPassed());

        PhishingHistoryTesting phishingHistory = new PhishingHistoryTesting();
        phishingHistory.removeVisitedSite("user-pishingrule-unit-test" ,
                "password-pishingrule-unit-test"
                , "www.google-unit-test.com");
    }

    /**
     * This method tests the case where there is no phishing attack and the website is not the same
     */
    @Test
    void NoPhishingAttackCaseDifferentCredentialsSameWebsiteTest() {
        SameCredentialsRuleChecker checker
                = new SameCredentialsRuleChecker("user-pishingrule-test" ,
                "password-pishingrule-test"
                , "www.google-unit-test.com");

        assertFalse(checker.checkRule().hasPassed());

        PhishingHistoryTesting phishingHistory = new PhishingHistoryTesting();
        phishingHistory.removeVisitedSite("user-pishingrule-test" ,
                "password-pishingrule-test"
                , "www.google-unit-test.com");
    }

    /**
     * This method tests the case where there is no phishing attack and the website and credentials are both different.
     */
    @Test
    void NoPhishingAttackCaseDifferentCredentialsDifferentWebsiteTest() {
        SameCredentialsRuleChecker checker
                = new SameCredentialsRuleChecker("user-pishingrule-test" ,
                "password-pishingrule-test"
                , "www.google-unit.com");

        assertFalse(checker.checkRule().hasPassed());

        PhishingHistoryTesting phishingHistory = new PhishingHistoryTesting();
        phishingHistory.removeVisitedSite("user-pishingrule-test" ,
                "password-pishingrule-test"
                , "www.google-unit.com");
    }

    @AfterEach
    void cleanUp() {
        PhishingHistoryTesting phishingHistory = new PhishingHistoryTesting();
        phishingHistory.removeVisitedSite("user-pishingrule-unit-test" ,
                "password-pishingrule-unit-test"
                , "www.google-unit-test.com");
    }

    @AfterAll
    static void cleanUpAll() {
        PhishingHistoryTesting phishingHistory = new PhishingHistoryTesting();
        phishingHistory.cleanVisitedSiteandLegitimateSite();
    }
}