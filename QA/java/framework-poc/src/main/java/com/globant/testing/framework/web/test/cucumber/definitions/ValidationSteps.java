package com.globant.testing.framework.web.test.cucumber.definitions;

import com.comcast.zucchini.TestContext;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import com.globant.testing.framework.web.test.pageobject.MethodDispatcher;

import static java.lang.String.format;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static com.globant.testing.framework.web.test.pageobject.MethodDispatcher.ElementType.ANY;

/**
 * Generic Cucumber steps for performing validations on fields/web elements of a POM
 *
 * @author Juan Krzemien
 */
public class ValidationSteps {

    private final MethodDispatcher dispatcher = new MethodDispatcher();

    @And("^\"([^\"]*)\" field should be empty$")
    public void fieldIsNotEmpty(String fieldName) throws Throwable {
        String result = dispatcher.triggerMethodGetter(fieldName, ANY);
        assertEquals(result, "", fieldName + " is not empty");
    }

    @And("^\"([^\"]*)\" field is the same as previously entered$")
    public void fieldsValueIsTheSameAsPreviouslyEntered(String fieldName) throws Throwable {
        String result = dispatcher.triggerMethodGetter(fieldName, ANY);
        String previouslyEntered = TestContext.getCurrent().get(fieldName);
        assertEquals(result, previouslyEntered, format("Field [%s] has a value [%s] which does not match previously entered one [%s]", fieldName, result, previouslyEntered));
    }

    @And("^\"([^\"]*)\" field's value is \"([^\"]*)\"$")
    public void fieldsValueIs(String fieldName, String value) throws Throwable {
        // Attempt to match option with a value previously stored in context...
        String text = TestContext.getCurrent().get(value);
        String result = dispatcher.triggerMethodGetter(fieldName, ANY);
        // Prefer over literal value and compare...
        String toUse = text == null ? value : text;
        assertEquals(result, toUse, format("Field [%s] has a value [%s] which does not match [%s]", fieldName, result, toUse));
    }

    @And("^\"([^\"]*)\" field's value contains \"([^\"]*)\"$")
    public void fieldsValueContains(String fieldName, String value) throws Throwable {
        // Attempt to match option with a value previously stored in context...
        String text = TestContext.getCurrent().get(value);
        String result = dispatcher.triggerMethodGetter(fieldName, ANY);
        // Prefer over literal value and compare...
        String toUse = text == null ? value : text;
        assertTrue(result.contains(toUse), format("Field [%s] has a value [%s] which does not contain [%s]", fieldName, result, toUse));
    }

    @Then("^\"([^\"]*)\" is the same as previously entered in \"([^\"]*)\" field$")
    public void isTheSameAsPreviouslyEnteredInField(String fieldName, String previousFieldName) throws Throwable {
        // Retrieve value used in other steps...
        String previousFieldValue = TestContext.getCurrent().get(previousFieldName);
        // Get current field value
        String result = dispatcher.triggerMethodGetter(fieldName, ANY);
        // Compare
        assertEquals(result, previousFieldValue, format("Field [%s] has a value [%s] which does not match previously entered one [%s] in [%s]", fieldName, result, previousFieldValue, previousFieldName));
    }

    @And("^\"([^\"]*)\" contains value previously entered in \"([^\"]*)\" field$")
    public void containsValuePreviouslyEnteredInField(String fieldName, String previousFieldName) throws Throwable {
        // Retrieve value used in other steps...
        String previousFieldValue = TestContext.getCurrent().get(previousFieldName);
        // Get current field value
        String result = dispatcher.triggerMethodGetter(fieldName, ANY);
        // Compare
        assertTrue(result.contains(previousFieldValue), format("Field [%s] has a value [%s] which does not contain previously entered one [%s] in [%s]", fieldName, result, previousFieldValue, previousFieldName));
    }

}