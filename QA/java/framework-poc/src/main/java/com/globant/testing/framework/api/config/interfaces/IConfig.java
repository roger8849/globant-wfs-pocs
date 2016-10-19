package com.globant.testing.framework.api.config.interfaces;


import com.globant.testing.framework.api.config.Environment;

/**
 * @author Juan Krzemien
 */
public interface IConfig {

    /**
     * Flag indicating whether framework should run in DEBUG logging level or not
     *
     * @return true if DEBUG logging level is set, false otherwise
     */
    boolean isDebugMode();

    /**
     * HTTP(S) Proxy configuration retrieval from config file
     *
     * @return A {@link IProxy} implementation
     */
    IProxy Proxy();

    /**
     * Retrieves environment data from the one marked as active in config file
     *
     * @return Environment instance
     */
    Environment getActiveEnvironment();
}
