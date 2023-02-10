package org.zaproxy.addon.profilingproxy.improvements;

import org.zaproxy.addon.profilingproxy.Message;

/**
 * This class is the abstraction that all the improvements
 * will inherit from.
 */
public interface Improvement {
    
    /**
     * Checks for performance improvements
     * for the given message.
     *
     * @param data - The message to check for improvements.
     * @return a data transfer object with the information
     * about all the improvements for the given message.
     */
    ImprovementDTO getResult(Message data);
}
