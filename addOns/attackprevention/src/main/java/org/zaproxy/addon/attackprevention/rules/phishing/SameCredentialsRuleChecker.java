package org.zaproxy.addon.attackprevention.rules.phishing;

import org.zaproxy.addon.attackprevention.database.Credentials;
import org.zaproxy.addon.attackprevention.database.PhishingHistory;
import org.zaproxy.addon.attackprevention.rules.Rule;
import org.zaproxy.addon.attackprevention.rules.RuleDTO;

/**
 * This class checks if some user credentials
 * are being used in two different websites.
 *
 */
public class SameCredentialsRuleChecker extends Rule {

    private final String username;
    private final String password;
    private final String website;

    /**
     * Create a class that checks if the same password is used for two websites
     * @param username - The username of the user.
     * @param password - The password of the user.
     * @param website - The website where the credentials are being logged in.
     */
    public SameCredentialsRuleChecker(String username,String password,String website){
        this.username=username;
        this.password=password;
        this.website=website;
    }

    /**
     * This method verifies if the user credentials
     * may encompass a phishing attack.
     *
     * @return true if the user credentials
     * are used in two different websites.
     */
    @Override
    public RuleDTO checkRule() {

        PhishingHistory phishingHistory = new PhishingHistory();

        Credentials credentials = phishingHistory.getCredential(username, password);
        boolean result=phishingHistory.hasCredsUsedByOtherSites(credentials, website);
        return new RuleDTO(result);
    }
}

