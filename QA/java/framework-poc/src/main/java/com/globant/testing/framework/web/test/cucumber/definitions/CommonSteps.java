package com.globant.testing.framework.web.test.cucumber.definitions;

import com.comcast.zucchini.TestContext;
import com.globant.testing.framework.web.test.pageobject.MethodDispatcher;
import com.globant.testing.framework.web.utils.Generator;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Generic Cucumber steps for performing actions on fields/web elements of a POM
 *
 * @author Juan Krzemien
 */
public class CommonSteps {

    private final MethodDispatcher dispatcher = new MethodDispatcher();

    @And("^User clicks the \"([^\"]*)\" checkbox$")
    public void userClicksTheCheckbox(String checkboxName) throws Throwable {
        dispatcher.triggerMethodHandler(checkboxName, MethodDispatcher.ElementType.CHECKBOX);
    }

    @And("^User checks the \"([^\"]*)\" checkbox$")
    public void userChecksTheCheckbox(String checkboxName) throws Throwable {
        boolean isChecked = dispatcher.triggerMethodGetter(checkboxName, MethodDispatcher.ElementType.CHECKBOX);
        if (!isChecked) {
            dispatcher.triggerMethodHandler(checkboxName, MethodDispatcher.ElementType.CHECKBOX);
        }
    }

    @And("^User unchecks the \"([^\"]*)\" checkbox$")
    public void userUnchecksTheCheckbox(String checkboxName) throws Throwable {
        boolean isChecked = dispatcher.triggerMethodGetter(checkboxName, MethodDispatcher.ElementType.CHECKBOX);
        if (isChecked) {
            dispatcher.triggerMethodHandler(checkboxName, MethodDispatcher.ElementType.CHECKBOX);
        }
    }

    @And("^User clicks the \"([^\"]*)\" link$")
    public void userClicksTheLink(String linkName) throws Throwable {
        dispatcher.triggerMethodHandler(linkName, MethodDispatcher.ElementType.LINK);
    }

    @And("^User clicks the \"([^\"]*)\" button$")
    public void userClicksTheButton(String buttonName) throws Throwable {
        dispatcher.triggerMethodHandler(buttonName, MethodDispatcher.ElementType.BUTTON);
    }

    @When("^User enters \"([^\"]*)\" in \"([^\"]*)\" field$")
    public void userEntersInField(String text, String fieldName) throws Throwable {
        dispatcher.triggerMethodHandler(fieldName, MethodDispatcher.ElementType.TEXT, text);
        // Make available for possible later usage in other steps...
        TestContext.getCurrent().set(fieldName, text);
    }

    @And("^User enters (\\d+?) random alphanumeric chars in \"([^\"]*)\" field$")
    public void userEntersARandomAlphanumericStringInField(int number, String fieldName) throws Throwable {
        String randomString = Generator.alphanumericString(number);
        dispatcher.triggerMethodHandler(fieldName, MethodDispatcher.ElementType.TEXT, randomString);
        // Make available for possible later usage in other steps...
        TestContext.getCurrent().set(fieldName, randomString);
    }

    @And("^User enters (\\d+?) random numeric chars in \"([^\"]*)\" field$")
    public void userEntersARandomNumericStringInField(int number, String fieldName) throws Throwable {
        String randomString = Generator.numericString(number);
        dispatcher.triggerMethodHandler(fieldName, MethodDispatcher.ElementType.TEXT, randomString);
        // Make available for possible later usage in other steps...
        TestContext.getCurrent().set(fieldName, randomString);
    }

    @And("^User enters (\\d+?) random alphabetic chars in \"([^\"]*)\" field$")
    public void userEntersARandomAlphabeticStringInField(int number, String fieldName) throws Throwable {
        String randomString = Generator.alphabeticString(number);
        dispatcher.triggerMethodHandler(fieldName, MethodDispatcher.ElementType.TEXT, randomString);
        // Make available for possible later usage in other steps...
        TestContext.getCurrent().set(fieldName, randomString);
    }

    @And("^User enters a random email in \"([^\"]*)\" field$")
    public void userEntersARandomEmailStringInField(String fieldName) throws Throwable {
        String randomEmail = Generator.emailString();
        dispatcher.triggerMethodHandler(fieldName, MethodDispatcher.ElementType.TEXT, randomEmail);
        // Make available for possible later usage in other steps...
        TestContext.getCurrent().set(fieldName, randomEmail);
    }

    @When("^User enters today's date in \"([^\"]*)\" field$")
    public void userEntersTodaysDateInField(String fieldName) throws Throwable {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
        dispatcher.triggerMethodHandler(fieldName, MethodDispatcher.ElementType.TEXT, date);
        // Make available for possible later usage in other steps...
        TestContext.getCurrent().set(fieldName, date);
    }

    @When("^User selects \"([^\"]*)\" option in \"([^\"]*)\" drop down$")
    public void userSelectsInField(String option, String fieldName) throws Throwable {
        // Attempt to match option with a value previously stored in context...
        String text = TestContext.getCurrent().get(option);
        // Prefer over literal value...
        dispatcher.triggerMethodHandler(fieldName, MethodDispatcher.ElementType.SELECT, text == null ? option : text);
        // Make available for possible later usage in other steps...
        TestContext.getCurrent().set(fieldName, option);
    }

    @And("^User enters value for \"([^\"]*)\" in \"([^\"]*)\" field$")
    public void userEntersKnownValueInField(String oldFieldName, String newFieldName) throws Throwable {
        // Retrieve value used in other steps...
        String autoGenerateValue = TestContext.getCurrent().get(oldFieldName);
        // Set in field
        dispatcher.triggerMethodHandler(newFieldName, MethodDispatcher.ElementType.TEXT, autoGenerateValue);
        // Make available for possible later usage in other steps...
        TestContext.getCurrent().set(newFieldName, autoGenerateValue);
    }

    @And("^User hovers mouse over \"([^\"]*)\" field$")
    public void userMouseHoverOnField(String fieldName) throws Throwable {
        dispatcher.triggerMethodHandler(fieldName, MethodDispatcher.ElementType.ANY);
    }

    @And("^User clicks the \"([^\"]*)\" field")
    public void userClicksTheField(String fieldName) throws Throwable {
        dispatcher.triggerMethodHandler(fieldName, MethodDispatcher.ElementType.ANY);
    }

}
