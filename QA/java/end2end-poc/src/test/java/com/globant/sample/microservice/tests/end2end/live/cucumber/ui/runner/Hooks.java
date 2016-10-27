package com.globant.sample.microservice.tests.end2end.live.cucumber.ui.runner;

import com.globant.testing.framework.web.test.pageobject.AbstractCucumberHook;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import static java.lang.String.format;

/**
 * @author Juan Krzemien
 */
public class Hooks extends AbstractCucumberHook {

    @Before
    public void cucumberSetUp(Scenario scenario) {
        getLogger().info(format("Starting scenario [%s] execution", scenario.getName()));
        recreateDriver();
    }

    @After
    public void cucumberTearDown(Scenario scenario) {
        getLogger().info(format("Finished scenario [%s] execution", scenario.getName()));
        if (scenario.isFailed()) {
            scenario.embed(takeScreenshot(), "image/png");
        }
        destroyDriver();

        /*
         * WORK IN PROGRESS - POC
         *
         * Move to framework once proven.
         *
         * Also, try to remove XRay Maven's DependencyManagement from framework POM file too.
         *
         */
        /*URI jiraUrl = new URI("http://52.45.166.109:8080/");
        String user = "";
        String pass = "";
        try (AsyncXrayJiraRestClient client = (AsyncXrayJiraRestClient) new XrayRestAsyncRestClientFactory().createWithBasicHttpAuthentication(jiraUrl, user, pass)) {
            // Need access to real XRay sample here...
            String id = scenario.getId().split(";")[1];
            Long testId = Long.valueOf(id.substring(0, id.indexOf(":")));
            TestRun.Status status;
            switch (scenario.getStatus()) {
                case "failed":
                    status = FAIL;
                    break;
                case "skipped":
                    status = ABORTED;
                    break;
                case "pending":
                    status = EXECUTING;
                    break;
                case "undefined":
                    status = TODO;
                    break;
                default:
                    status = PASS;
                    break;
            }
            getLogger().info("Scenario ID was: " + testId);
            getLogger().info("Scenario result was: " + status);
            //client.getTestRunClient().updateStatus(testId, status);
        }*/
    }
}
