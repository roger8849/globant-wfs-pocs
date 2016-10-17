package com.globant.sample.microservice.tests.end2end.live.cucumber.ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static org.jboss.arquillian.graphene.Graphene.guardHttp;

/**
 * @author Juan Krzemien
 */
public class ResultsPage {

    @FindBy(css = "h3.r")
    private List<WebElement> results;

    public void selectResultNumber(int index) {
        guardHttp(results.get(index - 1)).click(); // Synchronize on whole page reload
    }

}
