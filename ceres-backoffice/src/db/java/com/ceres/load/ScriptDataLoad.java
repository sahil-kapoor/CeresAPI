package com.ceres.load;

import com.ceres.domain.entity.human.Gender;
import com.ceres.domain.entity.script.Script;
import com.ceres.domain.entity.script.ScriptCategory;
import com.ceres.domain.repository.script.ScriptCategoryRepository;
import com.ceres.domain.repository.script.ScriptRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by leonardo on 17/03/15.
 */
@Component
public class ScriptDataLoad implements DataLoad, Ordered {

    @Inject
    private ScriptCategoryRepository scriptCategoryRepository;

    @Inject
    private ScriptRepository scriptRepo;

    @Value("${script.source.folder}")
    private String sourcePath;

    @Value("${script.male.folder}")
    private String maleFolder;

    @Value("${script.female.folder}")
    private String femaleFolder;

    @Value("${script.default.category}")
    private String scriptCategory;

    @Value("${common.scripts}")
    private String textPlainScripts;

    @Value("${male.scripts}")
    private String textPlainMaleScripts;

    @Value("${female.scripts}")
    private String textPlainFemaleScripts;

    @Value("${script.type}")
    private String scripType;

    @Override
    public void load() {
        ScriptCategory standardCategory = createScriptCategory(scriptCategory);

        loadScripts(Arrays.asList(textPlainMaleScripts.split(",")), standardCategory, Gender.MALE);
        loadScripts(Arrays.asList(textPlainFemaleScripts.split(",")), standardCategory, Gender.FEMALE);
        loadScripts(Arrays.asList(textPlainScripts.split(",")), standardCategory, null);
    }

    private void loadScripts(List<String> scriptString, ScriptCategory standardCategory, Gender gender) {
        for (String k : scriptString) {
            Script script = prepareScript(standardCategory, k, gender);
            scriptRepo.save(script);
        }
    }

    private Script prepareScript(ScriptCategory standardCategory, String k, Gender gender) {
        String dependency = k.split(":")[0].trim();
        String mnemonic = k.split(":")[1].trim();
        String scriptName = k.split(":")[2].trim();

        Script script = new Script();
        script.setMnemonic(mnemonic);
        script.setName(scriptName);
        script.setTargetGender(gender);
        script.setCategory(standardCategory);

        if (!dependency.isEmpty()) {
            if (gender != null) {
                script.addAllDependency(scriptRepo.findByMnemonicAndTargetGender(dependency, gender));
            } else {
                script.addAllDependency(scriptRepo.findByMnemonic(dependency));
            }
        }

        script.setScriptContent(parseScriptContent(mnemonic, gender));
        return script;
    }

    private String parseScriptContent(String k, Gender gender) {
        try {
            String scriptContent;
            if (gender == null) {
                scriptContent = IOUtils.toString(this.getClass().getResource(sourcePath + k + scripType));
            } else if (gender.equals(Gender.FEMALE)) {
                scriptContent = IOUtils.toString(this.getClass().getResource(sourcePath + femaleFolder + k + scripType));
            } else {
                scriptContent = IOUtils.toString(this.getClass().getResource(sourcePath + maleFolder + k + scripType));
            }
            return scriptContent;
        } catch (IOException e) {
            return "";
        }
    }

    private ScriptCategory createScriptCategory(String scriptCategory) {
        ScriptCategory category = new ScriptCategory();
        category.setName(scriptCategory);
        return scriptCategoryRepository.save(category);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
