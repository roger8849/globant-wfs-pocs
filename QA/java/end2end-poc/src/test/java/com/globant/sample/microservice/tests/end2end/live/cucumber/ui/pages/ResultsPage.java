package com.globant.sample.microservice.tests.end2end.live.cucumber.ui.pages;

import com.globant.testing.framework.web.test.pageobject.PageObject;
import com.globant.testing.framework.web.test.pageobject.annotations.ActionOnField;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static com.globant.testing.framework.web.test.Conditions.toBeTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElements;

/**
 * @author Juan Krzemien
 */
public class ResultsPage extends PageObject {

    @FindBy(css = "h3.r")
    private List<WebElement> results;

    @ActionOnField("results")
    public void selectResultNumber(int index) {
        waitFor(toBeTrue(results, webElements -> webElements.size() > 0));
        waitFor(visibilityOfAllElements(results));
        results.get(index - 1).click();
    }

}
