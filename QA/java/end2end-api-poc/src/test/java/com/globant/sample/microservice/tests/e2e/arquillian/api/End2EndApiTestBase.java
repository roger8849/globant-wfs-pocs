package com.globant.sample.microservice.tests.e2e.arquillian.api;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;

import java.util.Optional;

import static io.restassured.http.ContentType.JSON;

/**
 * @author Juan Krzemien
 */
@RunWith(Arquillian.class)
@RunAsClient
public abstract class End2EndApiTestBase {

    private final ThreadLocal<RequestSpecification> clients = new ThreadLocal<RequestSpecification>() {
        @Override
        protected RequestSpecification initialValue() {
            return RestAssured.with().baseUri(getTargetUrl()).and().contentType(JSON).log().all();
        }
    };

    protected String getTargetUrl() {
        return Optional.ofNullable(System.getenv("SUT_ENVIRONMENT")).orElse(RestAssured.baseURI);
    }

    protected RequestSpecification client() {
        return clients.get();
    }

}
