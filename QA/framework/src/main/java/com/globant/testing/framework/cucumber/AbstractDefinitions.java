package com.globant.testing.framework.cucumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mkolisnyk.cucumber.reporting.CucumberResultsOverview;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;

import static com.globant.testing.framework.config.Framework.CONFIGURATION;
import static io.restassured.http.ContentType.JSON;
import static java.lang.String.format;

/**
 * @author Juan Krzemien
 */
public abstract class AbstractDefinitions {

    private static final String NO_URL_ERROR = "No base URL defined in config.yml file nor as SUT_ENVIRONMENT environmental variable";

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            CucumberResultsOverview results = new CucumberResultsOverview();
            results.setOutputDirectory("target/cucumber-results");
            results.setOutputName("cucumber-results");
            results.setSourceFile("target/cucumber.json");
            try {
                results.execute(true);
            } catch (Exception e) {
                System.err.println("ERROR GENERATING PRETTY REPORT: " + e.getMessage());
            }
        }));
    }

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper mapper;

    private ThreadLocal<RequestSpecification> spec = new ThreadLocal<RequestSpecification>() {
        @Override
        protected RequestSpecification initialValue() {
            return RestAssured.with().baseUri(getTargetUrl()).and().contentType(JSON).log().all();
        }
    };

    protected String getTargetUrl() {
        if (port > 0) return format("http://localhost:%s/", port); // Populated only by Spring (running In-Container mode)
        String envVar = System.getenv("SUT_ENVIRONMENT");
        if (envVar != null && !envVar.isEmpty()) return envVar;
        return CONFIGURATION.getBaseUrl().orElseThrow(() -> new IllegalStateException(NO_URL_ERROR)).toString();
    }

    protected RequestSpecification spec() {
        return spec.get();
    }

}
