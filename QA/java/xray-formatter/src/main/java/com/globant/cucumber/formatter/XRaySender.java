package com.globant.cucumber.formatter;

import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.response.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.lang.System.getenv;
import static java.util.Optional.ofNullable;

/**
 * @author Juan Krzemien
 */
public class XRaySender {

    private static final String EMPTY = "";

    private final boolean ableToRun;
    private final String url;
    private final String username;
    private final String password;

    public XRaySender(String urlSuffix) {
        Optional<String> envUrl = ofNullable(getenv("XRAY_URL"));
        Optional<String> envUsername = ofNullable(getenv("XRAY_USERNAME"));
        Optional<String> envPassword = ofNullable(getenv("XRAY_PASSWORD"));
        Properties p = getProperties();
        ableToRun = (envUrl.isPresent() && envUsername.isPresent() && envPassword.isPresent()) || !p.isEmpty();
        if (!ableToRun) {
            System.err.println("No XRay information detected. Won't send results to XRay...");
            this.url = EMPTY;
            this.username = EMPTY;
            this.password = EMPTY;
            return;
        }
        this.url = envUrl.orElse(p.getProperty("url")) + urlSuffix;
        this.username = envUsername.orElse(p.getProperty("username"));
        this.password = envPassword.orElse(p.getProperty("password"));
    }

    public void send(Appendable payload) {
        postWithCredentials(url, getBasicAuthScheme(username, password), payload.toString());
    }

    private AuthenticationScheme getBasicAuthScheme(String username, String password) {
        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName(username);
        authScheme.setPassword(password);
        return authScheme;
    }

    private void postWithCredentials(String url, AuthenticationScheme scheme, String payload) {
        if (!ableToRun || ofNullable(getenv("NO_XRAY_INTEGRATION")).isPresent()) return;
        RestAssured.baseURI = url;
        RestAssured.authentication = scheme;
        try {
            Response response = given()
                    //.log().all()
                    .contentType(JSON).and().body(payload)
                    .when()
                    .post();
            System.err.println("XRay HTTP response code " + response.getStatusCode());
        } catch (IllegalStateException ise) {
            System.err.println("Error while sending data to XRay: " + ise.getLocalizedMessage());
        }
    }

    private Properties getProperties() {
        Properties p = new Properties();
        try {
            InputStream is = Utils.class.getResourceAsStream("/xray.properties");
            if (is != null) {
                p.load(is);
            } else {
                System.err.println("[xray.properties] file not found in project's resources...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }
}
