package com.globant.testing.framework.web.test.cucumber;

import com.globant.testing.framework.web.test.pageobject.PageObject;
import org.slf4j.Logger;

import java.lang.reflect.ParameterizedType;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Class designed to help defining Cucumber steps for a <i>particular</i> POM
 *
 * @author Juan Krzemien
 */
public abstract class StepsFor<T extends PageObject> {

    private static final Logger LOG = getLogger(StepsFor.class);
    private final Class<T> currentPageType;
    //private T currentPage;

    public StepsFor() {
        this.currentPageType = getPageObjectType();
        /*if (!currentPageType.isAnnotationPresent(FocusFrames.class)) {
            this.currentPage = createInstance();
        }*/
    }

    @SuppressWarnings("unchecked")
    private Class<T> getPageObjectType() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

    private T createInstance() {
        T instance = null;
        try {
            instance = currentPageType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            LOG.error(e.getMessage(), e);
        }
        return instance;
    }

    protected T getCurrentPageForStep() {
        /*if (currentPage == null) return createInstance();
        return PAGE_TO_CONTEXT(currentPage);*/
        return createInstance();
    }

}
