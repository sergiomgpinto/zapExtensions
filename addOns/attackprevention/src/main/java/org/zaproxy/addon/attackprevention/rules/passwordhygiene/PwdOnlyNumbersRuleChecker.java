package org.zaproxy.addon.attackprevention.rules.passwordhygiene;

import org.zaproxy.addon.attackprevention.rules.Rule;
import org.zaproxy.addon.attackprevention.rules.RuleDTO;
import org.zaproxy.addon.attackprevention.utils.JSONLoader;

/**
 * This class checks if the given password is made of only numbers.
 *
 * @see Rule
 */
public class PwdOnlyNumbersRuleChecker extends Rule {

    private final String PASSED_RULE = JSONLoader.getLabel("PASSED_RULE");
    private final String INVALID_PASSWORD = JSONLoader.getLabel("INVALID_PASSWORD");
    private final String ONLY_NUMBERS_PASSWORD = JSONLoader.getLabel("FAILED_PWD_ONLY_NUMBERS_RULE_CHECKER");
    private final String pwd;

    /**
     * Class to check if password made of number only
     * @param pwd - Password from user.
     */
    public PwdOnlyNumbersRuleChecker(String pwd){
        this.pwd=pwd;
    }

    /**
     * This method checks if the password is made up only numbers.
     *
     * @return RuleDTO if password is good, or reason why password is bad.
     */
    @Override
    public RuleDTO checkRule() {
        //Enters here if the password is invalid.
        if (pwd == null || pwd.isEmpty() || pwd.trim().isEmpty()) {
            return new RuleDTO(false,INVALID_PASSWORD);
        }

        for(int i=0; i < pwd.length(); i++) {
            if (!Character.isDigit(pwd.charAt(i))) {
                return new RuleDTO(true,PASSED_RULE);
            }
        }
        return new RuleDTO(false,ONLY_NUMBERS_PASSWORD);
    }
}
