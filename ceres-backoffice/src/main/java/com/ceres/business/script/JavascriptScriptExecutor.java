package com.ceres.business.script;

import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Map;

/**
 * Created by leonardo on 19/03/15.
 */
@Named("JavascriptExecutor")
public class JavascriptScriptExecutor implements ScriptExecutor {

    @Value("${default.script.engine}")
    private String engineName;

    private ScriptEngine scriptEngine;

    @PostConstruct
    public void init() {
        scriptEngine = new ScriptEngineManager().getEngineByName(engineName);
    }

    @Override
    public Object execute(String script) {
        try {
            return scriptEngine.eval(script);
        } catch (ScriptException e) {
            return null;
        }
    }

    @Override
    public Object execute(String script, Map<String, Object> args) {

        args.forEach(scriptEngine::put);

        try {
            return scriptEngine.eval(script);
        } catch (ScriptException e) {
            return null;
        }

    }
}
