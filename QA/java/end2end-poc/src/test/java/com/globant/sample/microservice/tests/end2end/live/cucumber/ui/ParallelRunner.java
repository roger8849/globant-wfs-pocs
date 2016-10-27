package com.globant.sample.microservice.tests.end2end.live.cucumber.ui;

import com.globant.sample.microservice.tests.end2end.live.cucumber.ui.runner.TestRunner;
import com.globant.testing.framework.web.test.pageobject.ParallelWebTestsRunner;

/**
 * @author Juan Krzemien
 */
public class ParallelRunner {

    public static void main(String[] args) {
        new ParallelWebTestsRunner().run(new Class<?>[]{TestRunner.class});
    }
}
