package com.ceres.service.script;

import com.ceres.business.script.ScriptOrder;
import com.ceres.business.validation.Validator;
import com.ceres.domain.entity.human.Gender;
import com.ceres.domain.entity.script.Script;
import com.ceres.domain.entity.script.ScriptCategory;
import com.ceres.domain.repository.script.ScriptCategoryRepository;
import com.ceres.domain.repository.script.ScriptRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by leonardo on 22/03/15.
 */
@Service(value = "ScriptServiceImpl")
public final class ScriptServiceImpl implements ScriptService {

    private ScriptRepository scriptRepository;

    private ScriptCategoryRepository scriptCategoryRepository;

    private ScriptOrder orderService;

    private Validator<Script> scriptValidator;

    @Inject
    public ScriptServiceImpl(ScriptRepository scriptRepository, ScriptCategoryRepository scriptCategoryRepository, ScriptOrder orderService, Validator<Script> scriptValidator) {
        this.scriptRepository = scriptRepository;
        this.scriptCategoryRepository = scriptCategoryRepository;
        this.orderService = orderService;
        this.scriptValidator = scriptValidator;
    }

    @Override
    public Script getByMnemonic(String mnemonic) {
        return scriptRepository.findOneByMnemonic(mnemonic);
    }

    @Override
    public Set<Script> getNeededScripts(String categoryName, Gender gender) {
        if (scriptCategoryRepository.countByName(categoryName) == 0) {
            // THROW ERROR
        }

        ScriptCategory category = scriptCategoryRepository.findByName(categoryName);

        Set<Script> scripts = scriptRepository.findByCategoryAndTargetGenderOrTargetGenderIsNull(category, gender);

        cleanOppositeGenderScript(scripts, gender);

        Set<Script> executionOrder = orderService.getExecutionOrder(scripts);
        return executionOrder;

    }

    @Override
    public Script getScriptById(Long id) {
        return scriptRepository.findOne(id);
    }

    @Override
    public Set<Script> getAllScripts() {
        return new HashSet<>(scriptRepository.findAll());
    }

    @Override
    public void remove(Long id) {
        Set<Script> scriptsToDelete = new LinkedHashSet<>();
        Script script = scriptRepository.findOne(id);
        scriptsToDelete.addAll(scriptRepository.findByMnemonic(script.getMnemonic()));
        scriptsToDelete.addAll(scriptRepository.findByDependenciesIn(script));
        scriptRepository.delete(scriptsToDelete);
    }

    @Override
    public Boolean exists(Long id) {
        return scriptRepository.exists(id);
    }

    @Override
    public Script createOrUpdate(Script script) {
        if (scriptValidator.isValid(script)) {
            // TODO
        }

        return scriptRepository.save(script);
    }

    private synchronized void cleanOppositeGenderScript(Set<Script> scripts, Gender gender) {
        for (Script current : scripts) {
            if (current.getDependencies().size() > 0) {
                Iterator<Script> dependencyIterator = current.getDependencies().iterator();
                while (dependencyIterator.hasNext()) {
                    Script dependency = dependencyIterator.next();
                    if (dependency.getTargetGender() != gender) {
                        dependencyIterator.remove();
                    }
                }
            }
        }
    }
}
