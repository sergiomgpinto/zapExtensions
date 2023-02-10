package org.zaproxy.addon.attackprevention.database;

import org.zaproxy.addon.attackprevention.utils.DBLoader;
import org.zaproxy.addon.attackprevention.utils.JSONLoader;

import java.util.Map;
import java.util.Set;
import java.util.Optional;
import java.util.HashSet;

/**
 * This class gives the history of logins for users.
 */
public class PhishingHistory {

    final String visitedSitesPath = JSONLoader.getLabel("VisitedSitesPath");
    final String legitimateSitesPath = JSONLoader.getLabel("LegitimateSitesPath");
    final String lastRequestFile = JSONLoader.getLabel("LastRequestFile");
    Map<Credentials, Set<String>> visitedSites;
    Map<Credentials, Set<String>> legitimateSites;

    Map<String,String> lastRequest;

    public PhishingHistory() {
        visitedSites = Utils.stringToMap(DBLoader.loadDB(visitedSitesPath));
        legitimateSites = Utils.stringToMap(DBLoader.loadDB(legitimateSitesPath));
        lastRequest = Utils.stringToLastRequestMap(DBLoader.loadDB(lastRequestFile));
    }

    /**
     * This function gives the Credential object to be used since an existing
     * object will not match a newly created one.
     */
    public Credentials getCredential(String name, String pwd) {
        Optional<Credentials> matchingVisited = visitedSites.keySet().stream()
                .filter(x -> x.matches(name, pwd)).findAny();
        if (matchingVisited.isPresent()) {
            return matchingVisited.get();
        }

        Optional<Credentials> matchingLegitimate = legitimateSites.keySet().stream()
                .filter(x -> x.matches(name, pwd)).findAny();
        if (matchingLegitimate.isPresent()) {
            return matchingLegitimate.get();
        }

        Credentials result = Credentials.createCredentials(name, pwd);
        visitedSites.put(result, new HashSet<>());
        legitimateSites.put(result, new HashSet<>());

        return result;
    }

    public void storeRequest(String username, String pwd) {

        if (lastRequest.containsKey(username)) {
            lastRequest.clear();
            lastRequest.put(username, pwd);
        }
        else {
            lastRequest.put(username, pwd);
        }
        DBLoader.saveToDB(lastRequestFile, Utils.lastRequestMapToString(lastRequest));
    }

    public String getLastRequest() {

        String username = null;
        String pwd = null;
        for (Map.Entry<String, String> entry : lastRequest.entrySet()) {

            username = entry.getKey();
            pwd = entry.getValue();
            break;
        }
        return username + ":" + pwd;
    }

    /**
     * Store a site for the given credentials. This acts as a history of visits.
     * @param username - The user username.
     * @param password - The user password.
     * @param website - The website the user logged in.
     */
    public void addVisitedSite(String username, String password, String website) {

        Credentials credentials = getCredential(username, password);

        Set<String> sites = visitedSites.get(credentials);

        if (sites == null) {
            visitedSites.put(credentials, new HashSet<>(Set.of(website)));
        } else {
            sites.add(website);
        }
        DBLoader.saveToDB(visitedSitesPath, Utils.mapToString(visitedSites));
    }

    /**
     * Checks if there are other sites than the given one where these credentials have been used.
     * @param creds: the current site which should be excluded from the search.
     */
    public boolean hasCredsUsedByOtherSites(Credentials creds, String excludeSite) {
        HashSet<String> sites = (HashSet<String>) visitedSites.get(creds);
        if (sites == null || sites.isEmpty())
            return false;

        return sites.stream().anyMatch(x -> !x.equals(excludeSite));
    }

    /**
     * Make this site a legitimate site for the given credentials.
     */
    public void addLegitimateSite(Credentials creds, String site) {
        Set<String> sites = legitimateSites.get(creds);
        if (sites == null) {
            legitimateSites.put(creds, new HashSet<>(Set.of(site)));
        } else {
            sites.add(site);
        }

        DBLoader.saveToDB(legitimateSitesPath, Utils.mapToString(legitimateSites));
    }

    /**
     * Checks if a site is to be trusted for the given credentials.
     */
    public boolean isLegitimateSite(Credentials creds, String site) {
        HashSet<String> sites = (HashSet<String>) legitimateSites.get(creds);
        if (sites == null) {
            return false;
        }
        return sites.contains(site);
    }
}
