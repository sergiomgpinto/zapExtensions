package org.zaproxy.addon.attackprevention.database;

import org.zaproxy.addon.attackprevention.utils.DBLoader;
import org.zaproxy.addon.attackprevention.utils.JSONLoader;

import java.util.Set;

/**
 * This class gives the history of visited websites and legitimate websites.
 */
public class TyposquattingHistory {

    private final Set<String> visited;
    private final Set<String> legitimate;
    private final String visitedPath = JSONLoader.getLabel("VisitedPath");
    private final String legitimatePath = JSONLoader.getLabel("LegitimatePath");

    public TyposquattingHistory() {
        visited = Utils.stringToSet(DBLoader.loadDB(visitedPath));
        legitimate = Utils.stringToSet(DBLoader.loadDB(legitimatePath));
    }

    /**
     * This method checks if the given url has already been visited in the past.
     *
     * @param url - Typed website by the user.
     * @return true if and only if the given url has already been visited in the past.
     */
    public boolean isVisited(String url) {
        return visited.contains(url);
    }

    /**
     * This method checks if the given url is a legitimate website.
     *
     * @param url - Typed website by the user.
     * @return true if and only if the given url is a legitimate website.
     */
    public boolean isLegitimate(String url) {
        return legitimate.contains(url);
    }

    /**
     * This method saves the given url to the list of visited websites.
     *
     * @param url - Typed website by the user.
     */
    public void addVisited(String url) {
        visited.add(url);
        DBLoader.saveToDB(visitedPath, visited.toString());
    }

    /**
     * This method saves the given url to the list of legitimate websites.
     *
     * @param url - Typed website by the user.
     */
    public void addLegitimate(String url) {
        legitimate.add(url);
        DBLoader.saveToDB(legitimatePath, legitimate.toString());
    }

    /**
     * This method saves the given url to the list of visited websites.
     *
     * @return A set of strings of all the visited websites.
     */
    public Set<String> getVisited() {
        return visited;
    }

    /**
     * This method saves the given url to the list of legitimate websites.
     *
     * @return A set of strings of all the legitimate websites.
     */
    public Set<String> getLegitimate() {
        return legitimate;
    }

}
