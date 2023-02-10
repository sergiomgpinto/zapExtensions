package org.zaproxy.addon.attackprevention.rules.typosquatting;

import org.zaproxy.addon.attackprevention.rules.Rule;
import org.zaproxy.addon.attackprevention.rules.RuleDTO;

/**
 * This class checks if the website typed differs on a known
 * website by a replaced character, so that the user is warned
 * if he is sure that he wants to proceed to the website or not.
 * This class inherits the Rule interface.
 *
 * @see Rule
 */
public class ReplacedCharRuleChecker extends Rule{

    /**
     * This method compares two websites and checks if they are equal
     * except on a replaced character.
     *
     * @return true if and only if the websites differ on one replaced character
     */
    @Override
    public RuleDTO checkRule() {
        //Enters here if the typed website is invalid.
        if (typedWebsite == null || typedWebsite.isEmpty() || typedWebsite.trim().isEmpty()) {
            return new RuleDTO(false);
        }
        // Enters here if the typed website length is not equal to
        // the known website.
        if (typedWebsite.length() != knownWebsite.length()) {
            return new RuleDTO(false);
        }

        for (int i = 0; i < typedWebsite.length(); i++) {
            // Enters here if we encounter the char where both strings
            // start to differ.
            if (typedWebsite.charAt(i) != knownWebsite.charAt(i)) {

                String temp = typedWebsite.substring(0, i) + knownWebsite.charAt(i)
                        + typedWebsite.substring(i + 1);

                return new RuleDTO(temp.equals(knownWebsite));
            }
        }
        // Reaches here if the typed website is equal to the known website.
        return new RuleDTO(false);
    }
}
