package com.globant.testing.framework.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Juan Krzemien
 */
public interface Loggable {
    default Logger getLogger() {
        return LoggerFactory.getLogger(getClass());
    }
}
