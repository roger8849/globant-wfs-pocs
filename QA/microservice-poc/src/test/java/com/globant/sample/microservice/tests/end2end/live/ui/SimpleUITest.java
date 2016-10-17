package com.globant.sample.microservice.tests.end2end.live.ui;

import com.globant.sample.microservice.tests.end2end.live.cucumber.ui.pages.HomePage;
import com.globant.sample.microservice.tests.end2end.live.cucumber.ui.pages.ResultsPage;
import com.globant.testing.framework.ui.WebTest;
import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.graphene.page.Page;
import org.junit.Test;

/**
 * @author Juan Krzemien
 */
public class SimpleUITest extends WebTest {

    @Page
    ResultsPage results;

    @Test
    public void grapheneExample(@InitialPage HomePage homePage) {
        homePage.search("something");
        results.selectResultNumber(4);
        // Testing goes on...
    }
}
