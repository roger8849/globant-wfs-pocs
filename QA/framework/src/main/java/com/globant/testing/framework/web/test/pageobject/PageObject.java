package com.globant.testing.framework.web.test.pageobject;

import com.globant.testing.framework.web.test.cucumber.Context;
import com.globant.testing.framework.web.test.pageobject.annotations.DeletesCookies;
import com.globant.testing.framework.web.test.pageobject.annotations.FocusFrames;
import com.globant.testing.framework.web.test.pageobject.annotations.GetterForField;
import com.globant.testing.framework.web.test.pageobject.annotations.Url;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.LinkedHashSet;
import java.util.Set;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static org.openqa.selenium.support.ui.ExpectedConditions.frameToBeAvailableAndSwitchToIt;
import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe;

/**
 * Base class for modeling a Web Page using the Page Object pattern
 *
 * @author Juan Krzemien
 */
public abstract class PageObject extends PageCommon {

    public PageObject() {
        LOG.info(format("Creating new [%s] Page Object instance...", getClass().getSimpleName()));

        navigate();

        LOG.info(format("[%s] Page Object instance created...", getClass().getSimpleName()));

        Context.PAGE_TO_CONTEXT(this);

        // This would be awesome to have, but reduces test speed dramatically!
        //assertTrue(isVisible(), format("One or more elements were not visible when initializing Page Object [%s]!", getClass().getSimpleName()));
        requiresFrameFocus();
    }

    /**
     * If POM is marked with {@link FocusFrames}, processes annotation
     * values and switches WebDriver focus to the frames mentioned in the list, from first to last.
     */
    protected void requiresFrameFocus() {
        FocusFrames focusFrames = getClass().getAnnotation(FocusFrames.class);
        if (focusFrames != null) {
            String framePath = stream(focusFrames.value()).collect(joining(" -> "));
            switchTo().defaultContent();
            LOG.info(format("Page Object [%s] is marked with @FocusFrames, switching frame focus: Default Context -> %s", getClass().getSimpleName(), framePath));
            for (String frame : focusFrames.value()) {
                waitFor(frameToBeAvailableAndSwitchToIt(frame));
            }
        }
        /*else {
            LOG.info(format("Switch to default content for Page Object [%s]...", getClass().getSimpleName()));
            switchTo().defaultContent();
        }*/
    }

    /**
     * Pretty print POM name, when requested as a String
     *
     * @return the class name of the POM
     */
    @Override
    public String toString() {
        return getClass().getName();
    }

    private void deleteCookiesIfMarked() {
        DeletesCookies deletesCookies = getClass().getAnnotation(DeletesCookies.class);
        if (deletesCookies != null) {
            LOG.info(format("Page Object [%s] is marked with @DeletesCookies, deleting...", getClass().getSimpleName()));
            getDriver().manage().deleteAllCookies();
        }
    }

    private void navigate() {
        String envUrl = System.getenv("SUT_URL");
        if (envUrl != null && !envUrl.isEmpty()) {
            LOG.info(format("Environment variable SUT_URL present! Navigating to [%s]...", envUrl));
            goToUrl(envUrl);
        } else {
            Url urlMark = getClass().getAnnotation(Url.class);
            if (urlMark != null) {
                LOG.info(format("Page Object [%s] is marked with @Url, navigating to [%s]...", getClass().getSimpleName(), urlMark.value()));
                goToUrl(urlMark.value());
            }
        }
    }

    protected void goToUrl(String url) {
        deleteCookiesIfMarked();
        keepMainWindow(this);
        getDriver().get(url);
    }

    public void refresh() {
        getDriver().navigate().refresh();
        switchTo().defaultContent();
    }

    @GetterForField("browserTitle")
    public String getPageTitle() {
        return getDriver().getTitle();
    }

    public boolean isVisible() {
        getOwnWebElements().forEach(ExpectedConditions::visibilityOf);
        return true;
    }

    protected void switchWindows() {
        waitFor(numberOfWindowsToBe(2));
        String currentWindow = getDriver().getWindowHandle();
        Set<String> openWindows = new LinkedHashSet<>(getDriver().getWindowHandles());
        for (String openWindow : openWindows) {
            if (!openWindow.equals(currentWindow)) {
                switchTo().window(openWindow);
                break;
            }
        }
    }

    public <T extends PageObject> T keepMainWindow(T pageObjectWithFocusAfterWindowSwitch) {
        Set<String> openWindows = new LinkedHashSet<>(getDriver().getWindowHandles());
        openWindows.stream().filter(w -> !w.equals(Drivers.INSTANCE.getMainWindowHandler())).forEach(currentWindow -> getDriver().close());
        switchTo().window(Drivers.INSTANCE.getMainWindowHandler());
        switchTo().defaultContent();
        return Context.PAGE_TO_CONTEXT(pageObjectWithFocusAfterWindowSwitch);
    }
}
