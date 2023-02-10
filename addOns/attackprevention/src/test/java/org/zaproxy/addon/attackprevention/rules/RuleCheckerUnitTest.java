package org.zaproxy.addon.attackprevention.rules;

import org.junit.jupiter.api.Test;
import org.zaproxy.addon.attackprevention.utils.JSONLoader;
import org.zaproxy.addon.attackprevention.rules.passwordhygiene.PwdCrackLibRuleChecker;
import org.zaproxy.addon.attackprevention.rules.passwordhygiene.PwdOnlyNumbersRuleChecker;
import org.zaproxy.addon.attackprevention.rules.passwordhygiene.PwdTop500RuleChecker;
import org.zaproxy.addon.attackprevention.rules.typosquatting.CombosquattingRuleChecker;
import org.zaproxy.addon.attackprevention.rules.typosquatting.ExtraCharRuleChecker;
import org.zaproxy.addon.attackprevention.rules.typosquatting.MissingCharRuleChecker;
import org.zaproxy.addon.attackprevention.rules.typosquatting.SwappedCharRuleChecker;
import org.zaproxy.addon.attackprevention.rules.typosquatting.ReplacedCharRuleChecker;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

/**
 * This class incorporates all the unit tests for the rules directory which
 * includes:
 *
 * @see CombosquattingRuleChecker
 * @see ExtraCharRuleChecker
 * @see MissingCharRuleChecker
 * @see SwappedCharRuleChecker
 * @see ReplacedCharRuleChecker
 * @see PwdCrackLibRuleChecker
 * @see PwdOnlyNumbersRuleChecker
 *
 * @see PwdTop500RuleChecker
 */
class RuleCheckerUnitTest {

    private final String KNOWN_WEBSITE = "https://www.example.com";
    private final String PASSED_RULE = JSONLoader.getLabel("PASSED_RULE");
    private final String ONLY_NUMBERS_PWD = JSONLoader.getLabel("FAILED_PWD_ONLY_NUMBERS_RULE_CHECKER");

    /**
     * This method is a test for the successful cases of the
     * Combosquatting rule, in other words, when the rule is fulfilled.
     */
    @Test
    void CombosquattingInTypedWebsiteTest(){
        ArrayList<String> allGoodCases = new ArrayList<>();

        allGoodCases.add("https://www.example-new.com");
        allGoodCases.add("https://www.example-real.com");
        allGoodCases.add("https://www.example-official.com");
        allGoodCases.add("https://www.example-login.com");
        allGoodCases.add("https://www.example-search.com");
        allGoodCases.add("https://www.example-shop.com");
        allGoodCases.add("https://www.example-web.com");
        allGoodCases.add("https://www.example-user.com");
        allGoodCases.add("https://www.example-live.com");
        allGoodCases.add("https://www.example-en.com");

        Rule rule = new CombosquattingRuleChecker();

        for (String goodCaseWebsite: allGoodCases) {
            rule.setTypedWebsite(goodCaseWebsite);
            rule.setKnownWebsite(KNOWN_WEBSITE);
            assert rule.checkRule().hasPassed();
        }

    }

    /**
     * This method is a test for the unsuccessful cases of the
     * Combosquatting rule, in other words, when the rule is not fulfilled.
     */
    @Test
    void NoCombosquattingInTypedWebsiteTest(){
        ArrayList<String> allFailCases = new ArrayList<>();

        allFailCases.add("https://www.exmple.com");
        allFailCases.add("https://www.exaample.com");
        allFailCases.add("");
        allFailCases.add(" ");
        allFailCases.add(null);
        allFailCases.add("https://www.eaxmple.com");
        allFailCases.add("https://www.example-unknownCombosquattingWordInOurApp.com");
        allFailCases.add("https://www.exjmple.com");
        allFailCases.add("randomString");

        Rule rule = new CombosquattingRuleChecker();

        for (String failCaseWebsite: allFailCases) {
            rule.setTypedWebsite(failCaseWebsite);
            rule.setKnownWebsite(KNOWN_WEBSITE);
            assert !rule.checkRule().hasPassed();
        }
    }

    /**
     * This method is a test for the successful case of the
     * ExtraChar rule, in other words, when the rule is fulfilled.
     */
    @Test
    void ExtraCharInTypedWebsiteTest(){
        String testWebsite = "https://www.exaample.com";

        Rule rule = new ExtraCharRuleChecker();
        rule.setKnownWebsite(KNOWN_WEBSITE);
        rule.setTypedWebsite(testWebsite);
        assert rule.checkRule().hasPassed();
    }

    /**
     * This method is a test for the unsuccessful cases of the
     * ExtraChar rule, in other words, when the rule is not fulfilled.
     */
    @Test
    void NoExtraCharInTypedWebsiteTest(){
        ArrayList<String> allFailCases = new ArrayList<>();

        allFailCases.add("https://www.exmple.com");
        allFailCases.add("");
        allFailCases.add(" ");
        allFailCases.add(null);
        allFailCases.add("https://www.eaxmple.com");
        allFailCases.add("https://www.example-live.com");
        allFailCases.add("https://www.exjmple.com");
        allFailCases.add("randomString");

        Rule rule = new ExtraCharRuleChecker();

        for (String failCaseWebsite: allFailCases) {
            rule.setTypedWebsite(failCaseWebsite);
            rule.setKnownWebsite(KNOWN_WEBSITE);
            assert !rule.checkRule().hasPassed();
        }
    }

    /**
     * This method is a test for the successful case of the
     * MissingChar rule, in other words, when the rule is fulfilled.
     */
    @Test
    void MissingCharInTypedWebsiteTest(){

        String testWebsite = "https://www.exmple.com";

        Rule rule = new MissingCharRuleChecker();
        rule.setKnownWebsite(KNOWN_WEBSITE);
        rule.setTypedWebsite(testWebsite);
        assert  rule.checkRule().hasPassed();

    }

    /**
     * This method is a test for the unsuccessful case of the
     * MissingChar rule, in other words, when the rule is not fulfilled.
     */
    @Test
    void NoMissingCharInTypedWebsiteTest(){

        ArrayList<String> allFailCases = new ArrayList<>();

        allFailCases.add("https://www.exaample.com");
        allFailCases.add("");
        allFailCases.add(" ");
        allFailCases.add(null);
        allFailCases.add("https://www.eaxmple.com");
        allFailCases.add("https://www.example-live.com");
        allFailCases.add("https://www.exjmple.com");
        allFailCases.add("randomString");

        Rule rule = new MissingCharRuleChecker();

        for (String failCaseWebsite: allFailCases) {
            rule.setTypedWebsite(failCaseWebsite);
            rule.setKnownWebsite(KNOWN_WEBSITE);
            assert !rule.checkRule().hasPassed();
        }

    }

    /**
     * This method is a test for the successful case of the
     * ReplacedChar rule, in other words, when the rule is fulfilled.
     */
    @Test
    void ReplacedCharInTypedWebsiteTest(){
        String testWebsite = "https://www.exjmple.com";

        Rule rule = new ReplacedCharRuleChecker();
        rule.setKnownWebsite(KNOWN_WEBSITE);
        rule.setTypedWebsite(testWebsite);
        assert rule.checkRule().hasPassed();
    }

    /**
     * This method is a test for the unsuccessful cases of the
     * ReplacedChar rule, in other words, when the rule is not fulfilled.
     */
    @Test
    void NoReplacedCharInTypedWebsiteTest(){
        ArrayList<String> allFailCases = new ArrayList<>();

        allFailCases.add("https://www.exaample.com");
        allFailCases.add("");
        allFailCases.add(" ");
        allFailCases.add(null);
        allFailCases.add("https://www.eaxmple.com");
        allFailCases.add("https://www.example-live.com");
        allFailCases.add("https://www.exmple.com");
        allFailCases.add("randomString");

        Rule rule = new ReplacedCharRuleChecker();

        for (String failCaseWebsite: allFailCases) {
            rule.setTypedWebsite(failCaseWebsite);
            rule.setKnownWebsite(KNOWN_WEBSITE);
            assert !rule.checkRule().hasPassed();
        }
    }

    /**
     * This method is a test for the successful case of the
     * ReplacedChar rule, in other words, when the rule is fulfilled.
     */
    @Test
    void SwappedCharInTypedWebsiteTest(){
        String testWebsite = "https://www.eaxmple.com";

        Rule rule = new SwappedCharRuleChecker();
        rule.setKnownWebsite(KNOWN_WEBSITE);
        rule.setTypedWebsite(testWebsite);
        assert rule.checkRule().hasPassed();
    }

    /**
     * This method is a test for the unsuccessful cases of the
     * ReplacedChar rule, in other words, when the rule is not fulfilled.
     */
    @Test
    void NoSwappedCharInTypedWebsiteTest() {
        ArrayList<String> allFailCases = new ArrayList<>();

        allFailCases.add("https://www.exaample.com");
        allFailCases.add("");
        allFailCases.add(" ");
        allFailCases.add(null);
        allFailCases.add("https://www.exsmple.com");
        allFailCases.add("https://www.example-live.com");
        allFailCases.add("https://www.exmple.com");
        allFailCases.add("randomString");

        Rule rule = new SwappedCharRuleChecker();

        for (String failCaseWebsite: allFailCases) {
            rule.setTypedWebsite(failCaseWebsite);
            rule.setKnownWebsite(KNOWN_WEBSITE);
            assert !rule.checkRule().hasPassed();
        }
    }

    /**
     * This method is a test for the CrackLib Rule, when the given password is too short
     */
    @Test
    void ShortCracklibPwdTest(){

        String badPwd = "abc";

        Rule rule = new PwdCrackLibRuleChecker(badPwd);

        assertEquals("It''s WAY too short.", rule.checkRule().getResult());

    }

    /**
     * This method is a test for the CrackLib Rule, when the given password has not enough different characters.
     */
    @Test
    void NotEnoughDiffCharCracklibPwdTest(){

        String badPwd = "aaaaaaaaaaaaaaaaaa";

        Rule rule = new PwdCrackLibRuleChecker(badPwd);

        assert rule.checkRule().getResult().equals("It does not contain enough DIFFERENT characters.");

    }

    /**
     * This method is a test for the CrackLib Rule, when the given password is a GOOD password.
     */
    @Test
    void GoodCracklibPwdTest(){

        String goodPwd = "Ijd8!Hkdi84!AldjhfuakdOO0xf";

        Rule rule = new PwdCrackLibRuleChecker(goodPwd);

        assert rule.checkRule().getResult().equals(PASSED_RULE);

    }

    /**
     * This method is a test for when the password is made of only numbers.
     */
    @Test
    void BadOnlyNumberRuleCheckerTest(){
        String badPwd = "123456";
        Rule rule = new PwdOnlyNumbersRuleChecker(badPwd);
        assert rule.checkRule().getResult().equals(ONLY_NUMBERS_PWD);
    }

    /**
     * This method is a test for when the password is not made of only numbers.
     */
    @Test
    void GoodOnlyNumberRuleCheckerTest(){
        String goodPwd = "1A2B3C";

        Rule rule = new PwdOnlyNumbersRuleChecker(goodPwd);

        assert rule.checkRule().getResult().equals(PASSED_RULE);
    }

    /**
     * This method is a test for when the password is not made of only numbers.
     */
    @Test
    void GoodTop500RuleCheckerTest(){
        String goodPwd = "NOT-IN-TOP500";

        Rule rule = new PwdTop500RuleChecker(goodPwd);

        assert rule.checkRule().getResult().equals(PASSED_RULE);
    }
}
