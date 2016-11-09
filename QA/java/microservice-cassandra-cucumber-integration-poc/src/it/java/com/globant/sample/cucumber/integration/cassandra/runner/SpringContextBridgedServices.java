package com.globant.sample.cucumber.integration.cassandra.runner;

/**
 * @author Juan Krzemien
 */
public interface SpringContextBridgedServices {

    <T> T getService(Class<T> serviceType);
}
