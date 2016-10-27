package com.globant.testing.framework.web.test.cucumber;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Simple class for storing and retrieving objects that are being test based on the currently running
 * thread. This is useful because the "given", "when" and "then" implementations in Cucumber do not
 * share any common DI framework.
 *
 * @author Juan Krzemien
 */
public class TestContext {

    private static final ThreadLocal<TestContext> LOCAL = new ThreadLocal<>();

    private String name;
    private Map<String, Object> beans;

    /**
     * Constructs a new empty TestContext
     *
     * @param name Name to be assigned to the context
     */
    public TestContext(String name) {
        this(name, new HashMap<>());
    }

    /**
     * Create a new TestContext pre-populated with the given beans.
     *
     * @param name  Name to be assigned to the context
     * @param beans the named objects in this test context
     */
    public TestContext(String name, Map<String, Object> beans) {
        this.name = name;
        this.beans = beans;
    }

    /**
     * Return the current test context for this run of cucumber. This will be the object that
     * was registered using {@link TestContext#set(String, Object)}
     *
     * @return the test context
     */
    public static TestContext getCurrent() {
        return LOCAL.get();
    }

    /**
     * Set the test context for this thread run.
     *
     * @param context the object under test for this thread (suite run)
     */
    public static void setCurrent(TestContext context) {
        LOCAL.set(context);
    }

    /**
     * Release the test context for this run of cucumber from local storage. This DOES NOT
     * actually release any physical hardware reservations which must be done independently. This
     * simply removes the internal variable so all future calls to {@link TestContext#get(String)} will
     * return <code>null</code>.
     */
    public static void removeCurrent() {
        LOCAL.remove();
    }

    /**
     * Returns set of bean names
     *
     * @return Set of bean names
     */
    public Set<String> getAllKeys() {
        return this.beans.keySet();
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return super.toString() + String.format("[%s, %s]", this.name, this.beans.toString());
    }

    /**
     * Returns the name of this TestContext. Names are required to make Zucchini tests
     * easier to debug and understand.
     *
     * @return the name of this test context
     */
    public String name() {
        return name;
    }

    /**
     * Get an object in this test context by name
     *
     * @param <T> Type of the object
     * @param key the name of the object in this test context
     * @return the test object
     */
    public <T> T get(String key) {
        return (T) beans.get(key);
    }

    /**
     * Put a named object into this test context
     *
     * @param <T> Type of the object
     * @param key the name of the object
     * @param val the test object
     */
    public <T> void set(String key, T val) {
        beans.put(key, val);
    }
}
