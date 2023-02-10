package org.zaproxy.addon.attackprevention.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class tests whether the hygiene mechanism
 * can be enabled/disabled correctly and persistently.
 */
class PasswordHygieneHistoryUnitTest {

    boolean originalValue;

    /**
     * Enables the password hygiene functionality.
     */
    @BeforeEach
    void setUp() {
        PasswordHygieneHistoryTesting passwordHygieneHistory = new PasswordHygieneHistoryTesting();
        originalValue = passwordHygieneHistory.isMechanismEnabled();
        passwordHygieneHistory.setPasswordHygieneMechanismEnabled(true);
    }

    /**
     * This method verifies that the password hygiene is indeed enabled after setting it enabled in setUp().
     */
    @Test
    void isPasswordHygieneMechanismEnabledTest() {
        PasswordHygieneHistory passwordHygieneHistory = new PasswordHygieneHistory();
        assertTrue(passwordHygieneHistory.isMechanismEnabled());
    }

    /**
     * Restores the original value.
     */
    @AfterEach
    void cleanUp() {
        PasswordHygieneHistoryTesting passwordHygieneHistory = new PasswordHygieneHistoryTesting();
        passwordHygieneHistory.setPasswordHygieneMechanismEnabled(originalValue);
    }
}
