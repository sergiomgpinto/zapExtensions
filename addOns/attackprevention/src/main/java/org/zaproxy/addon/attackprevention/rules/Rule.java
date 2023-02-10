package org.zaproxy.addon.attackprevention.rules;

/**
 * This class is the interface abstraction for all the rules.
 */
public abstract class Rule {

    protected String knownWebsite;
    protected String typedWebsite;

    /**
     * This method verifies if the rule is triggered.
     *
     * @return RuleDTO with the information of running
     * the rule for the given parameters.
     */
    public abstract RuleDTO checkRule();

    /**
     * Sets the known website.
     * @param knownWebsite = Common known website or previously accessed website.
     */
    public void setKnownWebsite(String knownWebsite) {
        this.knownWebsite = knownWebsite;
    }

    /**
     * Sets the typed website.
     * @param typedWebsite = Typed website by the user.
     */
    public void setTypedWebsite(String typedWebsite) {
        this.typedWebsite = typedWebsite;
    }
}
