package org.zaproxy.addon.attackprevention.rules.passwordhygiene;

import static Libraries.cracklib.CrackLib.fascistLook;

import org.zaproxy.addon.attackprevention.rules.Rule;
import org.zaproxy.addon.attackprevention.rules.RuleDTO;
import org.zaproxy.addon.attackprevention.utils.JSONLoader;
import Libraries.cracklib.Packer;

/**
 * This class checks if the given password passes the cracklib password test.
 *
 * @see Rule
 */
public class PwdCrackLibRuleChecker extends Rule {

    private final String PASSED_RULE = JSONLoader.getLabel("PASSED_RULE");
    private final String INVALID_PASSWORD = JSONLoader.getLabel("INVALID_PASSWORD");
    private final String pwd;

    /**
     * Create class for Cracklib password test
     * @param pwd- Password from user.
     */
    public  PwdCrackLibRuleChecker(String pwd){
    this.pwd=pwd;
    }

    /**
     * This method checks if the given password passes the cracklib password test.
     * @return RuleDTO containing result if password is good, or the reason why password is bad.
     */
    @Override
    public RuleDTO checkRule() {

        //Enters here if the password is invalid.
        if (pwd == null || pwd.isEmpty() || pwd.trim().isEmpty()) {
            return new RuleDTO(false,INVALID_PASSWORD);
        }

        try (Packer p = new Packer("wordsDictCrackLib", "r")) {

            String msg = fascistLook(p, pwd, null);

            if ( msg != null) {
                return new RuleDTO(false,msg);
            } else {
                return new RuleDTO(true,PASSED_RULE);
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        return  new RuleDTO(true,PASSED_RULE);
    }
}