package com.ceres.service.script;

import com.ceres.domain.entity.human.Gender;
import com.ceres.domain.entity.script.Script;

import java.util.Set;

/**
 * Created by leonardo on 22/03/15.
 */
public interface ScriptService {
    Script getByMnemonic(String mnemonic);

    Set<Script> getNeededScripts(String category, Gender gender);

    Script getScriptById(Long id);

    Set<Script> getAllScripts();

    void remove(Long id);

    Boolean exists(Long id);

    Script createOrUpdate(Script script);

}
