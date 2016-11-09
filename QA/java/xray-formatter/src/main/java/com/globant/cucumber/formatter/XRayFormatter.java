package com.globant.cucumber.formatter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.globant.cucumber.formatter.models.XRay;
import com.globant.cucumber.formatter.models.XRayEvidence;
import com.globant.cucumber.formatter.models.XRayStep;
import com.globant.cucumber.formatter.models.XRayTest;
import gherkin.deps.net.iharder.Base64;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.*;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.globant.cucumber.formatter.Utils.now;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

/**
 * Cucumber plugin for building JSON payload to send to XRay JSON API (its own format, not Cucumber's)
 * Work In Progress
 *
 * @author Juan Krzemien
 */
public class XRayFormatter implements Formatter, Reporter {

    private static final String XRAY_FORMAT_ENDPOINT = "/rest/raven/1.0/import/execution";
    private static final String FAIL = "FAIL";
    private static final String PASS = "PASS";
    private static final Map<String, String> MIME_TYPES_EXTENSIONS = new HashMap<String, String>() {
        {
            put("image/bmp", "bmp");
            put("image/gif", "gif");
            put("image/jpeg", "jpg");
            put("image/png", "png");
            put("image/svg+xml", "svg");
            put("video/ogg", "ogg");
        }
    };

    private final XRay xRay;
    private final XRaySender sender;

    private XRayTest currentTest;
    private int examplesRemaining;

    public XRayFormatter() {
        this.xRay = new XRay();
        this.sender = new XRaySender(XRAY_FORMAT_ENDPOINT);
        xRay.getInfo().setStartDate(now());
        xRay.getInfo().setSummary("AUTOMATED TESTS EXECUTION SUMMARY - " + LocalDateTime.now().format(ISO_LOCAL_DATE) + " - " + LocalDateTime.now().format(ISO_LOCAL_TIME));
        xRay.getInfo().setDescription("This execution is automatically created when importing execution results from an external source");
    }

    public void syntaxError(String s, String s1, List<String> list, String s2, Integer integer) {
    }

    public void uri(String s) {
    }

    public void feature(Feature feature) {
    }

    public void scenarioOutline(ScenarioOutline scenarioOutline) {
        // If Outline, reset current test
        this.currentTest = new XRayTest();
        currentTest.setStart(now());
    }

    public void examples(Examples examples) {
        examplesRemaining = examples.getRows().size() - 1;
    }

    public void startOfScenarioLifeCycle(Scenario scenario) {
        // For "normal" scenarios, reset current test
        if (!isOutlineScenario(scenario)) {
            this.currentTest = new XRayTest();
            currentTest.setStart(now());
        }
    }

    public void background(Background background) {
    }

    public void scenario(Scenario scenario) {
        //currentTest.setComment(scenario.getDescription());
    }

    public void step(Step step) {
    }

    public void endOfScenarioLifeCycle(Scenario scenario) {
        String testStatus = getTestStatus();
        if (!isOutlineScenario(scenario)) {
            currentTest.setFinish(now());
            currentTest.setStatus(testStatus);
            multiplyTestPerTag(scenario);
            return;
        }
        currentTest.getExamples().add(testStatus);
        if (--examplesRemaining == 0) {
            currentTest.setFinish(now());
            currentTest.setStatus(currentTest.getExamples().stream().anyMatch(FAIL::equalsIgnoreCase) ? FAIL : PASS);
            multiplyTestPerTag(scenario);
        }
    }

    private boolean isOutlineScenario(Scenario scenario) {
        return scenario.getKeyword().contains("Outline");
    }

    private void multiplyTestPerTag(Scenario scenario) {
        // Multiply test per tag
        scenario.getTags().forEach(tag -> {
            String key = tag.getName().replace("@", "");
            XRayTest cloned = Utils.clone(currentTest);
            if (cloned != null) {
                cloned.setTestKey(key);
                xRay.getTests().add(cloned);
            }
        });
    }

    private String getTestStatus() {
        return currentTest.getSteps().stream().anyMatch(s -> !PASS.equals(s.getStatus())) ? FAIL : PASS;
    }

    public void done() {
    }

    public void close() {
        xRay.getInfo().setFinishDate(now());
        sender.send(toJsonString());
    }

    private StringWriter toJsonString() {
        final ObjectMapper om = new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true);
        final StringWriter stringWriter = new StringWriter();
        try {
            om.writeValue(stringWriter, xRay);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringWriter;
    }

    public void eof() {
    }

    public void before(Match match, Result result) {
    }

    public void result(Result result) {
        XRayStep step = new XRayStep();
        String status;
        switch (result.getStatus()) {
            case "skipped":
                status = "TODO";
                break;
            case "undefined":
                status = "TODO";
                break;
            case "passed":
                status = PASS;
                break;
            default:
                status = FAIL;
                break;
        }
        step.setStatus(status);
        step.setComment(result.getErrorMessage());
        //step.setEvidences(currentTest.getEvidences());
        currentTest.getSteps().add(step);
    }

    public void after(Match match, Result result) {
    }

    public void match(Match match) {
    }

    public void embedding(String s, byte[] bytes) {
        String extension = MIME_TYPES_EXTENSIONS.get(s);
        String fileName = "screenshot-" + System.currentTimeMillis() + "." + extension;

        XRayEvidence evidence = new XRayEvidence();
        evidence.setData(Base64.encodeBytes(bytes));
        evidence.setFilename(fileName);
        evidence.setContentType(s);

        currentTest.getEvidences().add(evidence);
    }

    public void write(String s) {
    }
}
