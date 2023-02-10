package org.zaproxy.addon.attackprevention.rules.typosquatting;

import org.zaproxy.addon.attackprevention.rules.Rule;
import org.zaproxy.addon.attackprevention.rules.RuleDTO;

/**
 * This class checks if the website typed differs on a known
 * website by two adjacent swapped characters, so that the user is warned
 * if he is sure that he wants to proceed to the website or not.
 * This class inherits the Rule interface.
 *
 * @see Rule
 */
public class SwappedCharRuleChecker extends Rule {

    /**
     * This method compares two websites and checks if they are equal
     * except on two adjacent swapped characters..
     * @return RuleDTO with result to be true if and only if the websites differ on two adjacent
     * swapped characters
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
                // Enters here if they only differ in the last character
                // which means they do not have two adjacent swapped
                // characters.
                if (typedWebsite.length() == i + 1) {
                    return new RuleDTO(false);
                }
                else{

                    if (typedWebsite.charAt(i + 1) == knownWebsite.charAt(i)
                       && typedWebsite.charAt(i) == knownWebsite.charAt(i + 1)) {

                        String temp = typedWebsite.substring(0, i) + knownWebsite.charAt(i)
                                + knownWebsite.charAt(i + 1)
                                + typedWebsite.substring(i + 2);

                        return new RuleDTO(temp.equals(knownWebsite));
                    }
                    else{
                        // Enters here if the websites do not differ on two adjacent characters
                        // but on a single one.
                        return new RuleDTO(false);
                    }
                }
            }
        }
        // Reaches here if the typed website is equal to the known website.
        return new RuleDTO(false);
    }
}
