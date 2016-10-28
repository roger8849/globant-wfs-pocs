package com.globant.testing.framework.web.test.cucumber;

import com.globant.testing.framework.api.logging.Loggable;
import com.globant.testing.framework.web.config.Framework;
import com.globant.testing.framework.web.enums.Browser;
import cucumber.api.junit.Cucumber;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.RuntimeOptionsFactory;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.junit.FeatureRunner;
import cucumber.runtime.junit.JUnitReporter;
import cucumber.runtime.model.CucumberFeature;
import gherkin.formatter.model.Tag;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerScheduler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.MINUTES;
import static org.springframework.test.util.ReflectionTestUtils.getField;

/**
 * @author Juan Krzemien
 */
public class ParallelCucumber extends Cucumber implements Loggable {

    private static final int TIMEOUT_MINUTES = 10;
    private final Runtime runtime;
    private final RuntimeOptions runtimeOptions;
    private final ClassLoader classLoader;

    /**
     * Constructor called by JUnit.
     *
     * @param clazz the class with the @RunWith annotation.
     * @throws java.io.IOException                         if there is a problem
     * @throws org.junit.runners.model.InitializationError if there is another problem
     */
    public ParallelCucumber(Class clazz) throws InitializationError, IOException {
        super(clazz);
        setScheduler(new ThreadPoolScheduler());

        classLoader = clazz.getClassLoader();
        RuntimeOptionsFactory runtimeOptionsFactory = new RuntimeOptionsFactory(clazz);
        runtimeOptions = runtimeOptionsFactory.create();
        ResourceLoader resourceLoader = new MultiLoader(classLoader);
        runtime = createRuntime(resourceLoader, classLoader, runtimeOptions);
    }

    @Override
    public List<FeatureRunner> getChildren() {
        List<FeatureRunner> oldChildren = super.getChildren();
        Set<Browser> browsers = Framework.CONFIGURATION.AvailableDrivers();
        List<FeatureRunner> newChildren = new ArrayList<>(oldChildren.size() * browsers.size());
        try {
            for (FeatureRunner featureRunner : oldChildren) {
                for (Browser browser : browsers) {
                    CucumberFeature cucumberFeature = (CucumberFeature) getField(featureRunner, "cucumberFeature");
                    JUnitReporter jUnitReporter = new JUnitReporter(runtimeOptions.reporter(classLoader), runtimeOptions.formatter(classLoader), runtimeOptions.isStrict());
                    cucumberFeature.getGherkinFeature().getTags().add(new Tag(browser.name(), -1));
                    newChildren.add(new RunnerDecorator(cucumberFeature, runtime, jUnitReporter, browser));
                }
            }
        } catch (InitializationError ie) {
            getLogger().error(ie.getLocalizedMessage(), ie);
        }
        return newChildren;
    }

    private static class RunnerDecorator extends FeatureRunner {

        private final Browser browser;

        public RunnerDecorator(CucumberFeature cucumberFeature, Runtime runtime, JUnitReporter reporter, Browser browser) throws InitializationError {
            super(cucumberFeature, runtime, reporter);
            this.browser = browser;
        }

        @Override
        public void run(RunNotifier notifier) {
            Context.BROWSER_TO_CONTEXT(browser);
            super.run(notifier);
        }
    }

    private static class ThreadPoolScheduler implements RunnerScheduler {
        private final ExecutorService executor;

        ThreadPoolScheduler() {
            String threads = System.getProperty("junit.parallel.threads", "5");
            int numThreads = Integer.parseInt(threads);
            this.executor = Executors.newFixedThreadPool(numThreads, new TestThreadPool());
        }

        @Override
        public void finished() {
            executor.shutdown();
            try {
                executor.awaitTermination(TIMEOUT_MINUTES, MINUTES);
            } catch (InterruptedException exc) {
                throw new RuntimeException(exc);
            }
        }

        @Override
        public void schedule(Runnable childStatement) {
            executor.submit(childStatement);
        }

        static class TestThreadPool implements ThreadFactory {
            private static final AtomicInteger poolNumber = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "TestPool-Thread-" + poolNumber.getAndIncrement());
            }
        }
    }
}
