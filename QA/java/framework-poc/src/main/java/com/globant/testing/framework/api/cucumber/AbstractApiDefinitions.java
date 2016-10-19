package com.globant.testing.framework.api.cucumber;

import com.globant.testing.framework.api.logging.Loggable;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.springframework.boot.context.embedded.LocalServerPort;

import java.net.MalformedURLException;

import static com.globant.testing.framework.api.config.Framework.CONFIGURATION;
import static io.restassured.http.ContentType.JSON;
import static java.lang.String.format;

/**
 * @author Juan Krzemien
 */
public abstract class AbstractApiDefinitions implements Loggable {

    private static final String NO_URL_ERROR = "No base URL defined in config.yml file nor as SUT_ENVIRONMENT environmental variable";

//    static {
//        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//            CucumberResultsOverview results = new CucumberResultsOverview();
//            results.setOutputDirectory("target/cucumber-results");
//            results.setOutputName("cucumber-results");
//            results.setSourceFile("target/cucumber.json");
//            try {
//                results.execute(true);
//            } catch (Exception e) {
//                System.err.println("ERROR GENERATING PRETTY REPORT: " + e.getMessage());
//            }
//        }));
//    }

    @LocalServerPort
    private int port;

    private ThreadLocal<RequestSpecification> spec = new ThreadLocal<RequestSpecification>() {
        @Override
        protected RequestSpecification initialValue() {
            return RestAssured.with().baseUri(getTargetUrl()).and().contentType(JSON).log().all();
        }
    };

    protected String getTargetUrl() {
        if (port > 0)
            return format("http://localhost:%s/", port); // Populated only by Spring (when running In-Container mode)
        String envVar = System.getenv("SUT_ENVIRONMENT");
        if (envVar != null && !envVar.isEmpty()) return envVar;
        try {
            return CONFIGURATION.getActiveEnvironment().getUrl().toString();
        } catch (MalformedURLException e) {
            getLogger().error(e.getLocalizedMessage(), e);
            throw new RuntimeException(e);
        }
    }

    protected RequestSpecification spec() {
        return spec.get();
    }

}
