package org.zaproxy.addon.profilingproxy.extensionapp.ui.improvements;

/**
 * This class is the abstraction to a record in the improvements table.
 **/
public class ImprovementsTableRecord {

    private final String typeOfImprovement;
    private final String sizeReduction;
    private final String url;
    private final String responseContent;


    public ImprovementsTableRecord(String typeOfImprovement, String sizeReduction, String url, String responseContent) {
        this.typeOfImprovement = typeOfImprovement;
        this.sizeReduction = sizeReduction;
        this.url = url;
        this.responseContent = responseContent;
    }

    public String getTypeOfImprovement() {
        return typeOfImprovement;
    }

    public String getSizeReduction() {
        return sizeReduction;
    }

    public String getUrl() {
        return url;
    }

    public String getResponseContent() {
        return responseContent;
    }
}
