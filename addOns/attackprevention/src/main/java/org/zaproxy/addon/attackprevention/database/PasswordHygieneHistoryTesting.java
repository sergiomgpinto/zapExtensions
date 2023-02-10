package org.zaproxy.addon.attackprevention.database;

import org.zaproxy.addon.attackprevention.utils.DBLoader;

/**
 * This class includes the methods of PasswordHygieneHistory
 * that are used specifically for testing purposes.
 *
 * @see PasswordHygieneHistory
 */
public class PasswordHygieneHistoryTesting extends PasswordHygieneHistory {

    public PasswordHygieneHistoryTesting() {
        super();
    }

    /**
     * Enables or disables password mechanism for testing purposes.
     */
    public void setPasswordHygieneMechanismEnabled(boolean enabled) {
        DBLoader.saveToDB(passwordHygieneMechanismFile, String.valueOf(enabled));
    }
}
