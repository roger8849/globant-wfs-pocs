package com.globant.testing.framework.web.test.pageobject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.lang.reflect.Field;
import java.util.List;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

/**
 * Package local abstract class contains Selenium's WebElement initialization logic.
 * It is common to both: {@link PageElement} and {@link PageObject} classes
 * <p>
 * It also provides a mechanism to retrieve all the WebElements defined by a POM at once.
 * <p>
 *
 * @author Juan Krzemien
 */
abstract class PageCommon extends WebDriverOperations {

    PageCommon() {
        //AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(getDriver(), CONFIGURATION.WebDriver().getExplicitTimeOut());
        //PageFactory.initElements(factory, this);
        try {
            initElements(this);
        } catch (Exception e) {
            LOG.error("Unable to initialize POM!", e);
        }
    }

    protected List<WebElement> getOwnWebElements() {
        Field[] fields = getClass().getDeclaredFields();
        return stream(fields).filter(f -> f.getType().isAssignableFrom(WebElement.class)).map(f -> {
            try {
                f.setAccessible(true);
                WebElement we = (WebElement) f.get(this);
                f.setAccessible(false);
                return we;
            } catch (IllegalAccessException e) {
                LOG.error("Could not retrieve WebElements from Page Object!", e);
                return null;
            }
        }).collect(toList());
    }

    /**
     * Used for decorating each of the fields.
     *
     * @param page The object to decorate the fields of
     */
    private void initElements(Object page) {
        Class<?> proxyIn = page.getClass();
        while (proxyIn != Object.class) {
            proxyFields(page, proxyIn);
            proxyIn = proxyIn.getSuperclass();
        }
    }

    private void proxyFields(Object page, Class<?> proxyIn) {
        FieldDecorator decorator = new CustomFieldDecorator(new DefaultElementLocatorFactory(getDriver()));
        Field[] fields = proxyIn.getDeclaredFields();
        for (Field field : fields) {
            Object value = decorator.decorate(page.getClass().getClassLoader(), field);
            if (value != null) {
                try {
                    field.setAccessible(true);
                    field.set(page, value);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
