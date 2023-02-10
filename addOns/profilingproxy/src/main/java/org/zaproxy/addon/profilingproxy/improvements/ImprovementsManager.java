package org.zaproxy.addon.profilingproxy.improvements;

import org.zaproxy.addon.profilingproxy.Message;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This class is the abstraction that manages all the messages
 * being checked for potential improvements.
 */
public class ImprovementsManager {

    List<Improvement> improvements;
    public ImprovementsManager() {
        improvements = new ArrayList<>();
        // Added in the beginning since we need to keep track of all the messages for
        // caching improvement.
        improvements.add(new CachingImprovement());
    }

    /**
     * Runs specific improvements for the given message.
     *
     * @param message - The message to check for improvements.
     * @return a data transfer object with the information about all
     * the improvements for the given message.
     */
    public List<ImprovementDTO> runImprovements(Message message) {
        mapToImprovement(message);
        return improvements.stream().map(x -> x.getResult(message)).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * Maps the message content body to the specific improvements.
     * Caching improvement can be run for every message.
     *
     * @param message - The message to check for improvements.
     */
    public void mapToImprovement(Message message) {
        cleanImprovementsExceptCache();

        if (message.isCSS() || message.isJS()) {
            improvements.add(new ReduceJSandCSSImprovement());
        } else if (message.isPNG()) {
            improvements.add(new ReducePNGImprovement());
        }
    }

    /**
     * Removes all the improvements except the caching one. This is done
     * so that we do not run every possible improvement for each message.
     */
    private void cleanImprovementsExceptCache() {
        improvements.removeIf(improvement -> !(improvement instanceof CachingImprovement));
    }
}
