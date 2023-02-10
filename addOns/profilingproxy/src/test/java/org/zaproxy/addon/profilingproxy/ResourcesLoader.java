package org.zaproxy.addon.profilingproxy;

/**
 * This class is used to load resources for the given resource file.
 * It is only used by the test classes.
 */
public class ResourcesLoader {

    private ResourcesLoader() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Loads the resource file with the given name.
     * @param resource - the name of the resource file.
     * @return the resource file as a string.
     */
    public static String getResourcesPath(String resource) {
        String ABSOLUTE_USER_DIR_PATH = System.getProperty("user.dir");
        String ZAP_EXTENSIONS = "zap-extensions";
        String ZA_PROXY = "zaproxy";
        String zapExtPath;

        if (ABSOLUTE_USER_DIR_PATH.contains(ZAP_EXTENSIONS)){
            zapExtPath = ABSOLUTE_USER_DIR_PATH.substring(0,ABSOLUTE_USER_DIR_PATH
                    .indexOf(ZAP_EXTENSIONS));
        } else {
            zapExtPath = ABSOLUTE_USER_DIR_PATH.substring(0,ABSOLUTE_USER_DIR_PATH
                    .indexOf(ZA_PROXY));
        }
        return zapExtPath + resource;
    }
}
