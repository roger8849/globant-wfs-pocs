package com.globant.testing.framework.api.config.interfaces;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Juan Krzemien
 */
public interface Locatable {
    URL getUrl() throws MalformedURLException;

}
