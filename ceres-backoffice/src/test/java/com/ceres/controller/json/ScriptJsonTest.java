package com.ceres.controller.json;


import com.ceres.domain.entity.human.Gender;
import com.ceres.domain.entity.script.Script;
import org.junit.Before;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by Leonardo on 25/01/2015.
 */
public class ScriptJsonTest extends JsonTester {

    private static final String SCRIPT_JSON = "{jsonID:1,id:null,name:Porcentagem de Gordura,mnemonic:PORCENTAGEM_GORDURA,targetGender:FEMALE,scriptContent:return 10 + 10;,dependencies:[],category:null}";
    Script script;

    @Before
    public void prepare() throws JAXBException {
        initializeJsonMapper();

        script = new Script();
        script.setName("Porcentagem de Gordura");
        script.setMnemonic("PORCENTAGEM_GORDURA");
        script.setCategory(null);
        script.setTargetGender(Gender.FEMALE);
        script.setScriptContent("return 10 + 10;");
    }


    @Override
    public void shouldSerializeToJson() throws IOException {
        String json = mapper.writeValueAsString(script).replace("\"", "");

        assertEquals(SCRIPT_JSON, json);
    }

    @Override
    public void shouldDeserializeFromJson() throws IOException {
        String json = mapper.writeValueAsString(script);
        Script scriptDeserialized = mapper.readValue(json, Script.class);

        assertNotNull(scriptDeserialized);
        assertTrue(script.equals(scriptDeserialized));
    }
}
