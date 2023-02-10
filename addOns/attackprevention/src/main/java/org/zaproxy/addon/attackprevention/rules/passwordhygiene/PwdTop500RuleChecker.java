package org.zaproxy.addon.attackprevention.rules.passwordhygiene;

import org.zaproxy.addon.attackprevention.rules.Rule;
import org.zaproxy.addon.attackprevention.rules.RuleDTO;
import org.zaproxy.addon.attackprevention.utils.JSONLoader;
import org.zaproxy.addon.attackprevention.utils.DBLoader;

import java.util.HashSet;
import java.util.Set;

/**
 * This class checks if the given password is one of the 500 most used passwords.
 *
 * @see Rule
 */
public class PwdTop500RuleChecker extends Rule {

    private final String PASSED_RULE = JSONLoader.getLabel("PASSED_RULE");
    private final String INVALID_PASSWORD = JSONLoader.getLabel("INVALID_PASSWORD");
    private final String TOP_500_PWD_PATH = JSONLoader.getLabel("TOP_500_PASSWORDS");
    private final String TOP_500_PASSWORD = JSONLoader.getLabel("FAILED_PWD_TOP_500_RULE_CHECKER");
    private final String pwd;

    /**
     * create a class than checks if the database with 500 most used passwords contains the given password.
     *
     * @param pwd - Password from user.
     */
    public PwdTop500RuleChecker(String pwd){
        this.pwd=pwd;
    }

    /**
     * This method checks if the database with 500 most used passwords contains the given password.
     *
     * @return null if password is good, or (String) reason why password is bad.
     */
    @Override
    public RuleDTO checkRule() {
        //Enters here if the password is invalid.
        if (pwd == null || pwd.isEmpty() || pwd.trim().isEmpty()) {
            return new RuleDTO(false,INVALID_PASSWORD);
        }

        String data = DBLoader.loadDB(TOP_500_PWD_PATH);

        HashSet<String> top500PwdSet =  new HashSet<>(Set.of
                (data.substring(1, data.length() - 1).split(", ")));

        for (String top500Pwd:top500PwdSet) {
            if (pwd.equals(top500Pwd)) {
                 return new RuleDTO(false,TOP_500_PASSWORD);
            }
        }
         return new RuleDTO(true,PASSED_RULE);
    }
}
