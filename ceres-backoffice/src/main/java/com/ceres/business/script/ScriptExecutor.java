package com.ceres.business.script;

import java.util.Map;

/**
 * Created by leonardo on 10/03/15.
 */
public interface ScriptExecutor {

    Object execute(String script);

    Object execute(String script, Map<String, Object> args);

}
