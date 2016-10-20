package es.cuatrogatos.jira.xray.rest.client.core.internal.json;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import es.cuatrogatos.jira.xray.rest.client.api.domain.*;
import es.cuatrogatos.jira.xray.rest.client.core.internal.json.util.XrayJiraDateFormatter;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.atlassian.jira.rest.client.internal.json.GenericJsonArrayParser;
import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.atlassian.jira.rest.client.internal.json.JsonParseUtil;

/**
 * Created by lucho on 12/08/16.
 */
public class TestRunJsonParser implements JsonObjectParser<TestRun> {
    private final static DefectJsonParser defectParser=new DefectJsonParser();
    private final static EvidenceJsonParser evidenceParser=new EvidenceJsonParser();
    private final static TestStepJsonParser testStepParser=new TestStepJsonParser();
    private final static ExampleJsonParser exampleParser=new ExampleJsonParser();

    public final static String KEY_ID="id";
    public final static String KEY_STATUS="status";
    public final static String KEY_EXECBY="executedBy";
    public final static String KEY_ASSIGNEE="assignee";
    public final static String KEY_STARTEDON="startedOn";
    public final static String KEY_FINISHEDON="finishedOn";
    public final static String KEY_EXAMPLES="examples";
    public final static String KEY_COMMENT="comment";
    public final static String KEY_DEFECTS="defects";
    public final static String KEY_EVIDENCES="evidences";
    public final static String KEY_TESTSTEPS="steps";
    // CUMCUMBER TESTS
    public final static String KEY_SCENARIO="scenario";
    public final static String KEY_SCENARIO_OUTLINE="scenarioOutline";


    public TestRunJsonParser(){
    }

    public TestRun parse(JSONObject jsonObject) throws JSONException {
        jsonObject.put("self",""); // TODO: ADD URI.
        URI selfUri = JsonParseUtil.getSelfUri(jsonObject);
        String key =" THERE IS NO KEY FOR TEST RUN AT X-RAY DIRECT REST API"; // TODO: GET THE ISSUE KEY
        Long id = Long.valueOf(jsonObject.getLong(KEY_ID));
        TestRun.Status status=getStatus(jsonObject);
        GenericJsonArrayParser arrayParser=new GenericJsonArrayParser(defectParser);
        Iterable<Defect> defects=arrayParser.parse(jsonObject.getJSONArray(KEY_DEFECTS));
        arrayParser=new GenericJsonArrayParser(evidenceParser);
        Iterable<Evidence> evidences=arrayParser.parse(jsonObject.getJSONArray(KEY_EVIDENCES));
        Iterable<Example> examples=null;
        Iterable<TestStep> testSteps=null;

        Date startedOn = null;
        Date finishedOn = null;
        String executedBy = null;
        String assignee = null;
        
        try {
            if (!jsonObject.isNull(KEY_STARTEDON)) {
                startedOn = new XrayJiraDateFormatter().parse(jsonObject.getString(KEY_STARTEDON));
            }
            if (!jsonObject.isNull(KEY_FINISHEDON)) {
                finishedOn= new XrayJiraDateFormatter().parse(jsonObject.getString(KEY_FINISHEDON));
            }
            if (!jsonObject.isNull(KEY_EXECBY)) {
                executedBy = jsonObject.getString(KEY_EXECBY);
            }
            if (!jsonObject.isNull(KEY_ASSIGNEE)) {
                assignee = jsonObject.getString(KEY_ASSIGNEE);
            }
            if (!jsonObject.isNull(KEY_TESTSTEPS)) {
                arrayParser=new GenericJsonArrayParser(testStepParser);
                testSteps=arrayParser.parse(jsonObject.getJSONArray(KEY_TESTSTEPS));
            }
            if (!jsonObject.isNull(KEY_EXAMPLES)) {
                arrayParser=new GenericJsonArrayParser(exampleParser);
                examples=arrayParser.parse(jsonObject.getJSONArray(KEY_EXAMPLES));
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new JSONException(e.getMessage());
        }

        TestRun res=new TestRun(selfUri,key,id,status,startedOn,finishedOn,assignee,executedBy,defects,evidences,parseComment(jsonObject),examples,testSteps);
        return res;
    }

    //TODO: ADD SUPPORT FOR CUSTOM STATUSES.
    private TestRun.Status getStatus(JSONObject jsonObject) throws JSONException {
        if(jsonObject.get(KEY_STATUS).equals("TODO")){
            return TestRun.Status.TODO;
        }
        if(jsonObject.get(KEY_STATUS).equals("EXECUTING")){
            return TestRun.Status.EXECUTING;
        }
        if(jsonObject.get(KEY_STATUS).equals("ABORTED")){
            return TestRun.Status.ABORTED;
        }
        if(jsonObject.get(KEY_STATUS).equals("FAIL")){
            return TestRun.Status.FAIL;
        }
        if(jsonObject.get(KEY_STATUS).equals("PASS")){
            return TestRun.Status.PASS;
        }
        return null;
    }

    private Comment parseComment(JSONObject jsonObject){
        try {
            return new Comment(jsonObject.getString(KEY_COMMENT), jsonObject.getString(KEY_COMMENT));
        }catch(JSONException jE){
            return new Comment("","");
        }
    }



}
