package com.globant.sample.microservice.tests.end2end.live.cucumber.ui.runner;

import com.globant.testing.framework.api.logging.Loggable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import es.cuatrogatos.jira.xray.rest.client.api.domain.TestRun;
import es.cuatrogatos.jira.xray.rest.client.core.internal.async.AsyncXrayJiraRestClient;
import es.cuatrogatos.jira.xray.rest.client.core.internal.async.XrayRestAsyncRestClientFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static es.cuatrogatos.jira.xray.rest.client.api.domain.TestRun.Status.*;

/**
 * @author Juan Krzemien
 */
public class Hooks implements Loggable {

    @Before
    public void setUp(Scenario scenario) {

    }

    @After
    public void tearDown(Scenario scenario) throws URISyntaxException, IOException {
        /*
         * WORK IN PROGRESS - POC
         *
         * Move to framework once proven.
         *
         * Also, try to remove XRay Maven's DependencyManagement from framework POM file too.
         *
         */
        URI jiraUrl = new URI("http://52.45.166.109:8080/");
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
        }
    }
}
