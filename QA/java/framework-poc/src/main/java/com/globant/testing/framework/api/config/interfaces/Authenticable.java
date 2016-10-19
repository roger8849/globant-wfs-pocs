package com.globant.testing.framework.api.config.interfaces;

import com.globant.testing.framework.api.config.Credentials;

/**
 * @author Juan Krzemien
 */
public interface Authenticable {
    Credentials getCredentials();
}
