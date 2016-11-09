package com.globant.cucumber.formatter;

import cucumber.runtime.formatter.CucumberJSONFormatter;

import java.io.StringWriter;

/**
 * @author Juan Krzemien
 */
public class CucumberXRayFormatter extends CucumberJSONFormatter {

    private static final String CUCUMBER_FORMAT_ENDPOINT = "/rest/raven/1.0/import/execution/cucumber";

    private final XRaySender sender;
    private final Appendable appendable;

    public CucumberXRayFormatter() {
        this(new StringWriter());
    }

    public CucumberXRayFormatter(Appendable appendable) {
        super(appendable);
        this.sender = new XRaySender(CUCUMBER_FORMAT_ENDPOINT);
        this.appendable = appendable;
    }

    @Override
    public void done() {
        super.done();
        sender.send(appendable);
    }
}
