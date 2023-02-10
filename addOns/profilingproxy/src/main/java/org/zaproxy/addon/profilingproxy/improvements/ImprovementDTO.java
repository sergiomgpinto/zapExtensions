package org.zaproxy.addon.profilingproxy.improvements;

/**
 * This class is the abstraction to a data transfer object for an improvement.
 * It is responsible for transferring the return of an improvement to a given
 * message to the UI.
 */
public class ImprovementDTO {

    private int sizeReduction;
    private String url;
    private String responseBody;
    private boolean improvementIsPossible;
    private boolean isReduceJSFile;
    private boolean isReduceCSSFile;
    private final boolean isCachingImprovement;
    private final boolean isReduceJSandCSSImprovement;
    private final boolean isReducePNGImprovement;

    public ImprovementDTO(boolean isCachingImprovement, boolean isReduceJSandCSSImprovement, boolean isReducePNGImprovement) {
        this.isCachingImprovement = isCachingImprovement;
        this.isReduceJSandCSSImprovement = isReduceJSandCSSImprovement;
        this.isReducePNGImprovement = isReducePNGImprovement;
    }

    public int getSizeReduction() {
        return sizeReduction;
    }

    public String getSizeReductionToString() {
        if (isCachingImprovement) {
            return "";
        }
        else {
            return String.valueOf(sizeReduction);
        }
    }

    public String getUrl() {
        return url;
    }

    public String getResponseBody() {
        if (!isCachingImprovement) {
            return "";
        }
        else {
            return responseBody;
        }
    }

    public boolean isImprovementIsPossible() {
        return improvementIsPossible;
    }

    public void setSizeReduction(int sizeReduction) {
        this.sizeReduction = sizeReduction;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setResponseBody(byte[] responseBody) {
        this.responseBody = new String(responseBody);
    }

    public void setImprovementIsPossible(boolean improvementIsPossible) {
        this.improvementIsPossible = improvementIsPossible;
    }

    public void setReduceJSFile(boolean reduceJSFile) {
        isReduceJSFile = reduceJSFile;
    }

    public void setReduceCSSFile(boolean reduceCSSFile) {
        isReduceCSSFile = reduceCSSFile;
    }

    /**
     * This method returns the string representation to the type of improvement
     * this DTO represents. It is used to display in the status panel.
     *
     * @return the string representation to the type of improvement this DTO is.
     */
    public String getTypeOfImprovement() {
        if (isCachingImprovement) {
            return "Caching Optimization";
        } else if (isReduceJSandCSSImprovement) {
            if (isReduceJSFile) {
                return "Reduce JS File";
            } else if (isReduceCSSFile) {
                return "Reduce CSS File";
            }
        } else if (isReducePNGImprovement) {
            return "Reduce PNG File";
        }
        // Never reaches.
        return "";
    }
}
