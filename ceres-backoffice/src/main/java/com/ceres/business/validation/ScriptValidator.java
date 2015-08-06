package com.ceres.business.validation;

import com.ceres.domain.entity.script.Script;

import javax.inject.Named;

/**
 * Created by leonardo on 02/05/15.
 */
@Named("ScriptValidator")
public class ScriptValidator implements Validator<Script> {

    @Override
    public boolean isValid(Script script) {
        // TODO
        return true;
    }

}
