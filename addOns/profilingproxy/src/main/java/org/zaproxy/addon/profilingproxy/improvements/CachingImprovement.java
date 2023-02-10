package org.zaproxy.addon.profilingproxy.improvements;

import org.zaproxy.addon.profilingproxy.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This class is the abstraction for the caching
 * improvement.
 *
 * @see Improvement
 */
public class CachingImprovement implements Improvement {

    // url -> response content/body
    private final HashMap<String, ArrayList<byte[]>> cache;

    public CachingImprovement(){
        cache = new HashMap<>();
    }

    /**
     * If for the given message the pair url -> response body
     * already exists in cache then a potential caching
     * improvement is found.
     *
     * @param data - The message to check for the cache
     * improvement.
     * @return a data transfer object with the information
     * about the caching improvement for the given message.
     **/
    @Override
    public ImprovementDTO getResult(Message data) {

        String messageUrl = data.getUrl();
        byte[] messageBody = data.getResponseBody();
        ImprovementDTO improvementDTO = new ImprovementDTO(true,
                false,
                false);

        if (cache.containsKey(messageUrl)) {

            for (byte[] cachedBody : cache.get(messageUrl)) {
                if (Arrays.equals(cachedBody, messageBody)) {
                    improvementDTO.setImprovementIsPossible(true);
                    improvementDTO.setUrl(messageUrl);
                    improvementDTO.setResponseBody(messageBody);
                    return improvementDTO;
                }
            }
            cache.get(messageUrl).add(messageBody);
            improvementDTO.setImprovementIsPossible(false);
        }
        else {
            ArrayList<byte[]> cachedBodies = new ArrayList<>();
            cachedBodies.add(messageBody);
            cache.put(messageUrl, cachedBodies);
            improvementDTO.setImprovementIsPossible(false);
        }
        return improvementDTO;
    }
}
