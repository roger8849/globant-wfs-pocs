package es.cuatrogatos.jira.xray.rest.client.core.internal.json.gen;

import com.google.common.collect.Iterables;
import es.cuatrogatos.jira.xray.rest.client.api.domain.TestRun;
import es.cuatrogatos.jira.xray.rest.client.api.domain.TestStep;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by lucho on 23/08/16.
 */
public class TestRunUpdateJsonGenerator extends TestRunJsonGenerator {
    private static final String KEY_ADD="add";
    private static final String KEY_REMOVE="remove";
    private static final TestStepUpdateJsonGenerator testStepGenerator=new TestStepUpdateJsonGenerator();

    public JSONObject generate(TestRun testRun) throws JSONException {

        JSONObject filteredJSON=super.generate(testRun);
        filteredJSON.remove(TestRunJsonGenerator.KEY_ID);
        filteredJSON.remove(TestRunJsonGenerator.KEY_EXECBY);
        filteredJSON.remove(TestRunJsonGenerator.KEY_STARTEDON);
        filteredJSON.remove(TestRunJsonGenerator.KEY_FINISHEDON);
        filteredJSON.remove(TestRunJsonGenerator.KEY_STATUS);

        if(testRun.getVersion()!=0){

            if(testRun.getOldVersion().getStatus()!=null){
                if(!testRun.getStatus().name().equals(testRun.getOldVersion().getStatus().name())){
                filteredJSON.put(TestRunJsonGenerator.KEY_STATUS,testRun.getStatus().name());
                }
            }
            // TODO: THINK ABOUT REMOVE THIS INSTANCEOF
            if(filteredJSON.opt(TestRunJsonGenerator.KEY_DEFECTS) instanceof JSONObject){
               //UPDATE NOTHING TO DO
                if(((JSONObject) filteredJSON.opt(TestRunJsonGenerator.KEY_DEFECTS)).opt(KEY_ADD)==null
                        &&
                        ((JSONObject) filteredJSON.opt(TestRunJsonGenerator.KEY_DEFECTS)).opt(KEY_REMOVE) ==null)
                    filteredJSON.remove(TestRunJsonGenerator.KEY_DEFECTS);
            }
            // TODO: THINK ABOUT REMOVE THIS INSTANCEOF
            if(filteredJSON.opt(TestRunJsonGenerator.KEY_DEFECTS) instanceof JSONArray || filteredJSON.opt(TestRunJsonGenerator.KEY_DEFECTS)==null){
                filteredJSON.remove(TestRunJsonGenerator.KEY_DEFECTS);
            }
            // TODO: THINK ABOUT REMOVE THIS INSTANCEOF
            if(filteredJSON.opt(TestRunJsonGenerator.KEY_EVIDENCES) instanceof JSONObject){
                //UPDATE NOTHING TO DO?
            }
            // TODO: THINK ABOUT REMOVE THIS INSTANCEOF
            if(filteredJSON.opt(TestRunJsonGenerator.KEY_EVIDENCES) instanceof JSONArray  || filteredJSON.opt(TestRunJsonGenerator.KEY_DEFECTS)==null){
                filteredJSON.remove(TestRunJsonGenerator.KEY_EVIDENCES);
            }

            // FILTERING THE TEST STEPS WHICH NEEDS UPDATE
            if(filteredJSON.opt(TestRunJsonGenerator.KEY_TESTSTEPS)!=null){
                Collection<TestStep> testSteps=new ArrayList<TestStep>();
                Iterables.addAll(testSteps,testRun.getSteps());
                for(TestStep step: testRun.getSteps()){
                    if(step.getVersion()==0) // TEST STEP NO NEEDS UPDATE, REMOVE FROM COLLECTION
                    {
                        testSteps.remove(step);
                    }
                }
                filteredJSON.remove(TestRunJsonGenerator.KEY_TESTSTEPS); // REMOVE THE JSON, THEN REBUILD IT
                if(!testSteps.isEmpty()){ // REBUILD
                    JSONArray jsonTestSteps=new JSONArray();
                    for(TestStep step: testSteps){
                        jsonTestSteps.put(testStepGenerator.generate(step));
                    }
                    filteredJSON.put(TestRunJsonGenerator.KEY_TESTSTEPS,jsonTestSteps); // REATTACH ON THE FINAL JSON
                }

            }
            if(testRun.getComment().getRaw().equals(testRun.getOldVersion().getComment().getRaw())){
                filteredJSON.remove(TestRunJsonGenerator.KEY_COMMENT);
            }
        }else {
            filteredJSON.remove(TestRunJsonGenerator.KEY_DEFECTS);
            filteredJSON.remove(TestRunJsonGenerator.KEY_COMMENT);
            filteredJSON.remove(TestRunJsonGenerator.KEY_EVIDENCES);
            filteredJSON.remove(TestRunJsonGenerator.KEY_TESTSTEPS);
        }

            return filteredJSON;}

}
