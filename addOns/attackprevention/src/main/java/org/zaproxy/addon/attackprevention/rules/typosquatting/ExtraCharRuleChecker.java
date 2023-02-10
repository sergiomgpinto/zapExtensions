package org.zaproxy.addon.attackprevention.rules.typosquatting;

import org.zaproxy.addon.attackprevention.rules.Rule;
import org.zaproxy.addon.attackprevention.rules.RuleDTO;

/**
 * This class checks if the website typed differs on an extra
 * character comparing to a known website, so that the user
 * is warned if he is sure that he wants to proceed to
 * the website or not.
 * This class inherits the Rule interface.
 *
 * @see Rule
 */
public class ExtraCharRuleChecker extends Rule {

    /**
     * This method compares two websites and checks if they are equal
     * except on one extra character.
     * @return RuleDTO with result to be true if and only if the websites differ on one extra character
     */
    @Override
    public RuleDTO checkRule() {
        //Enters here if the typed website is invalid.
        if (typedWebsite == null || typedWebsite.isEmpty() || typedWebsite.trim().isEmpty()) {
            return new RuleDTO(false);
        }
        // Enters here if the typed website length does not only differ on one character
        // from the known website.
        if (typedWebsite.length() != knownWebsite.length() + 1) {
            return new RuleDTO(false);
        }

        for (int i = 0; i < knownWebsite.length(); i++) {
            // Enters here if we encounter the char where both strings
            // start to differ.
            if (knownWebsite.charAt(i) != typedWebsite.charAt(i)) {

                String temp = typedWebsite.substring(0,i) + typedWebsite.substring(i + 1);
                return new RuleDTO(temp.equals(knownWebsite));
            }
        }
        // Reaches here if the typed website has one extra character at the end.
        return new RuleDTO(true);
    }
}
