package com.ceres.service.script;

import com.ceres.domain.entity.script.ScriptCategory;
import com.ceres.domain.repository.script.ScriptCategoryRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by leonardo on 22/03/15.
 */
@Service
//@Scope("prototype")
public final class ScriptCategoryServiceImpl implements ScriptCategoryService {

    private ScriptCategoryRepository scriptCategoryRepository;

    @Override
    public ScriptCategory getByName(String name) {
        return scriptCategoryRepository.findByName(name);
    }

    @Override
    public ScriptCategory getById(Long id) {
        return scriptCategoryRepository.findOne(id);
    }

    @Inject
    public void setScriptCategoryRepository(ScriptCategoryRepository scriptCategoryRepository) {
        this.scriptCategoryRepository = scriptCategoryRepository;
    }
}
