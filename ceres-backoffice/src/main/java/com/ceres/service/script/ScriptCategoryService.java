package com.ceres.service.script;

import com.ceres.domain.entity.script.ScriptCategory;

/**
 * Created by leonardo on 22/03/15.
 */
public interface ScriptCategoryService {

    ScriptCategory getByName(String name);

    ScriptCategory getById(Long id);

}
