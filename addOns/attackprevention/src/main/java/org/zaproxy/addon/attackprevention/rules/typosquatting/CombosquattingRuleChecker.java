package org.zaproxy.addon.attackprevention.rules.typosquatting;

import org.zaproxy.addon.attackprevention.rules.Rule;
import org.zaproxy.addon.attackprevention.rules.RuleDTO;

import java.util.ArrayList;

/**
 * This class checks if the website typed differs on a known
 * website by a word that appears legitimate, so that the
 * user is warned if he is sure that he wants to proceed to
 * the website or not.
 * This class inherits the Rule interface.
 *
 * @see Rule
 */
public class CombosquattingRuleChecker extends Rule {

    ArrayList<String> combosquattingWords = new ArrayList<>();

    /**
     * Create class to check if url contain combosquatting words
     */
    public CombosquattingRuleChecker() {

        combosquattingWords.add("-new");
        combosquattingWords.add("-real");
        combosquattingWords.add("-official");
        combosquattingWords.add("-login");
        combosquattingWords.add("-search");
        combosquattingWords.add("-shop");
        combosquattingWords.add("-web");
        combosquattingWords.add("-user");
        combosquattingWords.add("-live");
        combosquattingWords.add("-en");
    }
    /**
     * This method compares two websites and checks if they are equal
     * except one appended word.
     *
     * @return RuleDTO with result to be true if and only if the websites differ one extra word
     */
    @Override
    public RuleDTO checkRule() {
        //Enters here if the typed website is invalid.
        if (typedWebsite == null || typedWebsite.isEmpty() || typedWebsite.trim().isEmpty()) {
            return new RuleDTO(false);
        }
        // Enters here if the typed website violates extra character rule
        // or words have the same length.
        if (typedWebsite.length() != knownWebsite.length()
        && typedWebsite.length() != knownWebsite.length() + 1) {
            // Enters here if typed website violates missing character rule.
            if (typedWebsite.length() + 1 == knownWebsite.length()) {
                return new RuleDTO(false);
            }
            else{
                for (int i = 0; i < typedWebsite.length(); i++) {
                    // Enters here if we encounter the char where both strings
                    // start to differ.
                    if (typedWebsite.charAt(i) != knownWebsite.charAt(i)) {

                        int j = i;
                        StringBuilder appendedWord = new StringBuilder();

                        while (typedWebsite.charAt(j) != knownWebsite.charAt(i)) {
                             appendedWord.append(typedWebsite.charAt(j));
                             j++;

                             // Enters here if after appended word websites are not equal.
                             if (j == typedWebsite.length()) {
                                 return new RuleDTO(false);
                             }
                        }
                        // Enters here if, for example,:
                        // typedWebsite -> www.google-live.com
                        // knownWebsite -> www.google.com
                        if (combosquattingWords.contains(appendedWord.toString())) {
                            String temp = typedWebsite.substring(0, i)
                                    + typedWebsite.substring(j);

                            return new RuleDTO(temp.equals(knownWebsite));
                        }
                        // Enters here if the appended word is unknown.
                        else {
                            return new RuleDTO(false);
                        }
                    }
                }
            }
        }
        // Reaches here if both websites violate replace character
        // or missing character rule.
        return new RuleDTO(false);
    }

}
