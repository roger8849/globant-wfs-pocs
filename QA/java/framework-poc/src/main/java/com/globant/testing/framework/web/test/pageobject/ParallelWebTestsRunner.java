package com.globant.testing.framework.web.test.pageobject;


import com.globant.testing.framework.web.config.Framework;
import com.globant.testing.framework.web.selenium.SeleniumServerStandAlone;
import com.globant.testing.framework.web.test.cucumber.Context;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Juan Krzemien
 */
public class ParallelWebTestsRunner {

    private final static SeleniumServerStandAlone seleniumServer = SeleniumServerStandAlone.INSTANCE;

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(seleniumServer::shutdown));
    }

    public void run(Class<?>[] classes) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        Framework.CONFIGURATION.AvailableDrivers().forEach(browser -> executor.execute(() -> {
                    Context.BROWSER_TO_CONTEXT(browser);
                    JUnitCore core = new JUnitCore();
                    core.addListener(new TextListener(System.out));
                    core.run(classes);
                }
        ));
        executor.shutdown();
    }
}
