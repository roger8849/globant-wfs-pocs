package com.globant.sample.microservice.tests.live.cucumber.ui.pages;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;

/**
 * @author Juan Krzemien
 */
@Location("/")
public class HomePage {

    @FindBy(id = "lst-ib")
    private WebElement searchBox;

    public void search(String criteria) {
        // Graphene tip: https://docs.jboss.org/author/pages/viewpage.action?pageId=66486914
        searchBox.sendKeys(criteria);
        guardAjax(searchBox).sendKeys(Keys.ENTER); // Synchronize only on last AJAX request
    }

}
