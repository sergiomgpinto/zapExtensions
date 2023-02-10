package org.zaproxy.addon.attackprevention.database;

import org.zaproxy.addon.attackprevention.utils.DBLoader;

import java.util.HashSet;
import java.util.Set;

/**
 * This class includes the methods of PhishingHistory that are
 * used specifically for testing purposes.
 *
 * @see PhishingHistory
 */
public class PhishingHistoryTesting extends PhishingHistory {

    public PhishingHistoryTesting() {
        super();
    }

    /**
     * Clears the database and memory for a specific visited website by a pair
     * of username and password user.
     */
    public void removeVisitedSite(String username, String password, String website) {
        Credentials credentials = getCredential(username, password);
        Set<String> sites = visitedSites.get(credentials);
        sites.remove(website);
        Set<String> updatedSites = new HashSet<>(sites);
        visitedSites.put(credentials, updatedSites);
        DBLoader.saveToDB(visitedSitesPath, Utils.mapToString(visitedSites));
    }

    /**
     * Cleans the history of visited sites and legitimate sites in memory
     * and in the database.
     */
    public void cleanVisitedSiteandLegitimateSite() {
        visitedSites.clear();
        legitimateSites.clear();
        DBLoader.saveToDB(visitedSitesPath, Utils.mapToString(visitedSites));
        DBLoader.saveToDB(legitimateSitesPath, Utils.mapToString(legitimateSites));
    }
}
