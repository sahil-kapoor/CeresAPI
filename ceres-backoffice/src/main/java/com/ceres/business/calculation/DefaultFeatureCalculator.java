package com.ceres.business.calculation;

import com.ceres.business.script.ScriptExecutor;
import com.ceres.domain.entity.human.Feature;
import com.ceres.domain.entity.script.Script;
import com.ceres.service.script.ScriptService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by leonardo on 23/03/15.
 */
@Named("DefaultFeatureCalculator")
public class DefaultFeatureCalculator implements Calculator<Feature> {

    @Value("${script.default.category}")
    private String DEFAULT_CATEGORY;

    private ScriptService scriptService;

    private ScriptExecutor executor;

    @Override
    public void calculate(Feature feature) {

        Set<Script> scripts = scriptService.getNeededScripts(DEFAULT_CATEGORY, feature.getPatient().getGender());

        Map<String, Object> arguments = CalculatorHelper.createCalcArguments(feature);

        scripts.forEach(script -> {
            Object result = executor.execute(script.getScriptContent(), arguments);
            arguments.put(script.getMnemonic(), result);
            feature.getCalcResults().put(script.getMnemonic(), (Double) result);
        });

        CalculatorHelper.roundDoubleArguments(feature.getFeatureParameters(), 3);

    }

    @Override
    public void calculateAll(Collection<Feature> features) {
        features.forEach(this::calculate);
    }

    @Inject
    @Qualifier(value = "GroovyExecutor")
    public void setExecutor(ScriptExecutor executor) {
        this.executor = executor;
    }

    @Inject
    public void setScriptService(ScriptService scriptService) {
        this.scriptService = scriptService;
    }
}
