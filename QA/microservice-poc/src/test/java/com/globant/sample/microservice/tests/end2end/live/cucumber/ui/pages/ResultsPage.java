package com.globant.sample.microservice.tests.end2end.live.cucumber.ui.pages;

import com.globant.testing.framework.web.test.pageobject.PageObject;
import com.globant.testing.framework.web.test.pageobject.annotations.ActionOnField;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * @author Juan Krzemien
 */
public class ResultsPage extends PageObject {

    @FindBy(css = "h3.r")
    private List<WebElement> results;

    @ActionOnField("results")
    public void selectResultNumber(int index) {
        results.get(index - 1).click();
    }

}
