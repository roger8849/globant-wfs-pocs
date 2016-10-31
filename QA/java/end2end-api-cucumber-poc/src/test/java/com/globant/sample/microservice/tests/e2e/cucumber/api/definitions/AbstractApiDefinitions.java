package com.globant.sample.microservice.tests.e2e.cucumber.api.definitions;

import com.globant.sample.microservice.tests.e2e.cucumber.api.Loggable;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import java.util.Optional;

import static io.restassured.http.ContentType.JSON;

/**
 * @author Juan Krzemien
 */
public abstract class AbstractApiDefinitions implements Loggable {

    private ThreadLocal<RequestSpecification> spec = new ThreadLocal<RequestSpecification>() {
        @Override
        protected RequestSpecification initialValue() {
            return RestAssured.with().baseUri(getTargetUrl()).and().contentType(JSON).log().all();
        }
    };

    protected String getTargetUrl() {
        return Optional.ofNullable(System.getenv("SUT_ENVIRONMENT")).orElse(RestAssured.baseURI);
    }

    protected RequestSpecification spec() {
        return spec.get();
    }

}
