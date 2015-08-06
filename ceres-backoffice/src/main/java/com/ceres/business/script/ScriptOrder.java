package com.ceres.business.script;

import com.ceres.domain.entity.script.Script;

import java.util.Set;

/**
 * Created by renato on 31/03/2015.
 */
public interface ScriptOrder {
    Set<Script> getExecutionOrder(Script script);

    Set<Script> getExecutionOrder(Set<Script> script);
}
