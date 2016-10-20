package com.globant.testing.framework.web.test.pageobject;

import com.globant.testing.framework.web.exceptions.MethodInvocationError;
import com.globant.testing.framework.web.test.pageobject.annotations.ActionOnField;
import com.globant.testing.framework.web.test.pageobject.annotations.FocusFrames;
import com.globant.testing.framework.web.test.pageobject.annotations.GetterForField;
import org.slf4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import static com.globant.testing.framework.web.test.cucumber.Context.PAGE_FROM_CONTEXT;
import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Public class in charge of triggering POM methods from Cucumber's Step Definition classes.
 *
 * @author Juan Krzemien
 */
public class MethodDispatcher {

    private static final Logger LOG = getLogger(MethodDispatcher.class);

    /**
     * Triggers a method in current POM in context annotated with {@link GetterForField} that matches
     * provided field name and element type criteria.
     *
     * @param fieldName Field name criteria referred in {@link GetterForField} value
     * @param type      Element Type criteria
     * @param <K>       A generic return type
     * @return Whatever the invoked method returns (Object)
     */
    @SuppressWarnings("unchecked")
    public <K> K triggerMethodGetter(String fieldName, ElementType type) {
        Class<GetterForField> annotation = GetterForField.class;
        try {
            return (K) invokeAnnotatedMethodMatchingName(annotation, fieldName, type);
        } catch (Exception e) {
            String msg = "Could not find a getter method that handles a field of type [%s] named [%s] in page object [%s]!\nAre you missing an %s annotation?";
            throw error(msg, type.name.toUpperCase(), fieldName, PAGE_FROM_CONTEXT().toString(), annotation.getSimpleName());
        }
    }

    /**
     * Triggers a method in current POM in context annotated with {@link ActionOnField} that matches
     * provided field name and element type criteria.
     *
     * @param fieldName Field name criteria referred in {@link ActionOnField} value
     * @param type      Element Type criteria
     * @param args      Arguments to forward to invoking method
     * @param <K>       Another POM
     * @return A POM instance returned from the invoked method
     * @throws MethodInvocationError Throws an error if invocation fails 3 times in a row or if no matching method was found
     */
    @SuppressWarnings("unchecked")
    public <K extends PageObject> K triggerMethodHandler(String fieldName, ElementType type, Object... args) throws MethodInvocationError {
        Class<ActionOnField> annotation = ActionOnField.class;
        try {
            return (K) invokeAnnotatedMethodMatchingName(annotation, fieldName, type, args);
        } catch (Exception e) {
            String msg = "Could not find a method that handles a field of type [%s] named [%s] in page object [%s]!\nAre you missing an %s annotation?";
            throw error(msg, type.name.toUpperCase(), fieldName, PAGE_FROM_CONTEXT(), annotation.getSimpleName());
        }
    }

    /**
     * Searches current in context POM looking for a specific annotation among its public methods.
     *
     * @param annotation Annotation to look for (currently, it is either {@link GetterForField} or {@link ActionOnField})
     * @param fieldName  Field name criteria referred in annotation's value
     * @param type       Element Type criteria
     * @param args       Arguments to forward to invoking method
     * @param <T>        Current in context POM type
     * @param <K>        Annotation generic type
     * @return Object returned from invoked matched method
     * @throws MethodInvocationError Throws an error if invocation fails 3 times in a row or if no matching method was found
     */
    @SuppressWarnings("unchecked")
    private <T extends PageObject, K extends Annotation> Object invokeAnnotatedMethodMatchingName(Class<K> annotation, String fieldName, ElementType type, Object... args) throws MethodInvocationError {
        T pageObject = (T) PAGE_FROM_CONTEXT().orElseThrow(() -> new IllegalArgumentException("Page object is null in context! You have not initialized any Page Object!"));
        final String fieldToSearch = fieldName.replaceAll(" ", "") + type.name;
        final Method[] methods = pageObject.getClass().getMethods();
        Set<Method> handlers = stream(methods).filter(m -> m.isAnnotationPresent(annotation)).collect(toSet());
        for (Method method : handlers) {
            String handlesFieldValue = readAnnotationValue(method.getAnnotation(annotation));
            if (handlerMatchesSearchedField(handlesFieldValue, fieldToSearch)) {
                try {
                    return method.invoke(pageObject, args);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throwErrorForException(e.getCause(), method.getName(), pageObject.toString());
                }
            }
        }

        /*
         *  If nothing was found, search for the requested field/type in POMs that can be returned from this one.
         *  This way, methods that operate on POM for iframes have a chance to be triggered.
         *
         *  Dispatcher ASSUMES that POMs returned by called methods "open the path" for the next Gherkin keywords that will be called...
         */
        handlers.clear();
        handlers = stream(methods).filter(m -> m.getReturnType().isAnnotationPresent(FocusFrames.class)).collect(toSet());
        for (Method method : handlers) {
            Method[] innerMethods = method.getReturnType().getMethods();
            Set<Method> innerHandlers = stream(innerMethods).filter(m -> m.isAnnotationPresent(annotation)).collect(toSet());
            for (Method innerMethod : innerHandlers) {
                String handlesFieldValue = readAnnotationValue(innerMethod.getAnnotation(annotation));
                if (handlerMatchesSearchedField(handlesFieldValue, fieldToSearch)) {
                    try {
                        pageObject = (T) method.getReturnType().newInstance();
                        return innerMethod.invoke(pageObject, args);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throwErrorForException(e.getCause(), method.getName(), pageObject.toString());
                    } catch (InstantiationException e) {
                        throw error(e, format("Error instantiating inner Page Object of page object [%s]!: %s", pageObject.toString(), e.getMessage()));
                    }
                }
            }
        }
        throw new IllegalStateException("Method to trigger not found");
    }

    /**
     * Check if current handler annotation value matches the field name + type we are looking for.
     *
     * @param handlesFieldValue Current handler annotation value
     * @param fieldToSearch     Field name + type we are looking for
     * @return True if matches, false otherwise.
     */
    private boolean handlerMatchesSearchedField(String handlesFieldValue, String fieldToSearch) {
        return handlesFieldValue.toLowerCase().startsWith(fieldToSearch.toLowerCase()) || handlesFieldValue.equalsIgnoreCase(fieldToSearch);
    }

    /**
     * @param annotation Annotation to retrieve its value field
     * @param <K>        Annotation generic type
     * @return String containing annotation's value field
     */
    private <K extends Annotation> String readAnnotationValue(K annotation) {
        try {
            Method value = annotation.getClass().getDeclaredMethod("value");
            return value.invoke(annotation).toString();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            LOG.error("readAnnotationValue: ", e);
        }
        return "";
    }

    private MethodInvocationError error(String message, Object... args) {
        return error(null, message, args);
    }

    private MethodInvocationError error(Throwable t, String message, Object... args) {
        String msg = format(message, args);
        LOG.error(msg, t);
        return new MethodInvocationError(msg, t);
    }

    private void throwErrorForException(Throwable t, String methodName, String pageObject) {
        if (t instanceof ClassCastException) {
            throw error(t, format("Method [%s] in Page Object [%s] MUST return a Page Object (a new one or itself)!", methodName, pageObject));
        }
        throw error(t, format("Error triggering method [%s] in page object [%s]!", methodName, pageObject));
    }

    /**
     * Public class detailing framework detectable suffixes for WebElement fields in POMs
     */
    public static class ElementType {

        public static final ElementType BUTTON = new ElementType("Button");
        public static final ElementType LINK = new ElementType("Link");
        public static final ElementType TEXT = new ElementType("Text");
        public static final ElementType SELECT = new ElementType("Select");
        public static final ElementType CHECKBOX = new ElementType("Check");
        public static final ElementType IFRAME = new ElementType("IFrame");
        public static final ElementType ANY = new ElementType("");
        private final String name;

        ElementType(String name) {
            this.name = name.toLowerCase();
        }
    }

}
