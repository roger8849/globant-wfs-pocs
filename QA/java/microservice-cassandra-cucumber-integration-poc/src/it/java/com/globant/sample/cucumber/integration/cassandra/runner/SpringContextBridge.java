package com.globant.sample.cucumber.integration.cassandra.runner;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author Juan Krzemien
 */
@Component
public class SpringContextBridge implements SpringContextBridgedServices, ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * A static method to lookup the SpringContextBridgedServices Bean in
     * the applicationContext. It is basically an instance of itself, which
     * was registered by the @Component annotation.
     *
     * @return the SpringContextBridgedServices, which exposes all the
     * Spring services that are bridged from the Spring context.
     */
    public static SpringContextBridgedServices services() {
        return applicationContext.getBean(SpringContextBridgedServices.class);
    }

    @Override
    public <T> T getService(Class<T> serviceType) {
        return applicationContext.getBean(serviceType);
    }
}
