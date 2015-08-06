package com.ceres.business.script;

import groovy.lang.GroovyShell;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.util.Map;

/**
 * Created by leonardo on 03/04/15.
 */
@Named("GroovyExecutor")
public class GroovyScriptExecutor implements ScriptExecutor {

    private GroovyShell shell;

    @PostConstruct
    public void init() {
        shell = new GroovyShell();
    }

    public GroovyScriptExecutor() {
        shell = new GroovyShell();
    }

    @Override
    public Object execute(String script) {
        return shell.evaluate(script);
    }

    @Override
    public Object execute(String script, Map<String, Object> args) {
        args.forEach(shell::setVariable);
        return shell.evaluate(script);
    }
}
