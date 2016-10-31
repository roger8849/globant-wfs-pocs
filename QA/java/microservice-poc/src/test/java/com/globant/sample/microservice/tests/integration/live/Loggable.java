package com.globant.sample.microservice.tests.integration.live;

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
