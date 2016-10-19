package com.globant.sample.microservice.tests.end2end.live.cucumber.ui.pages;

import com.globant.testing.framework.web.test.pageobject.PageObject;
import com.globant.testing.framework.web.test.pageobject.annotations.ActionOnField;
import com.globant.testing.framework.web.test.pageobject.annotations.Url;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Juan Krzemien
 */
@Url("http://www.google.com") // Can be overridden via env vars
public class HomePage extends PageObject{

    @FindBy(id = "lst-ib")
    private WebElement searchBoxText;

    @ActionOnField("searchBoxText")
    public ResultsPage enterCriteria(String criteria) {
        searchBoxText.sendKeys(criteria);
        searchBoxText.sendKeys(Keys.ENTER);
        return new ResultsPage();
    }

}
