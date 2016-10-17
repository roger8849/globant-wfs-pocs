package com.globant.testing.framework.ui;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

/**
 * @author Juan Krzemien
 */

@RunWith(Arquillian.class)
@RunAsClient
public class WebTest {

    @Drone
    private WebDriver browser;

    protected WebTest() {
        ChromeDriverManager.getInstance().setup();
    }
}
