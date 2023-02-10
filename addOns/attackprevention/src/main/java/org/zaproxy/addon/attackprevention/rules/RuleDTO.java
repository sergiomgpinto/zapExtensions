package org.zaproxy.addon.attackprevention.rules;

/**
 * This class is a data transfer object for the rules
 * which gathers the information returned from each
 * rule in a single entity.
 */
public class RuleDTO {

    private final boolean passed;
    private String result;

    /**
     * create the RuleDTO object
     * @param passed - flag signifying whether a rule was passed or not
     * @param result - the result outputted by the rule
     */
    public RuleDTO(boolean passed, String result) {
        this.passed = passed;
        this.result = result;
    }

    /**
     * create the RuleDTO object
     * @param passed - flag signifying whether a rule was passed or not
     */
    public RuleDTO(boolean passed) {
            this.passed = passed;
        }


    /**
     * method to check whether rule is passed or not
     * @return true if rule was passed or not
     */
    public boolean hasPassed() {
        return passed;
    }

    /**
     * method to get the result of the rule
     * @return the result of the rule as a string (can be an empty string)
     */
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
