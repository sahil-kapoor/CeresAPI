package com.ceres.business;

import com.ceres.business.calculation.DefaultFeatureCalculator;
import com.ceres.business.script.GroovyScriptExecutor;
import com.ceres.business.script.ScriptExecutor;
import com.ceres.domain.embedabble.Name;
import com.ceres.domain.entity.human.*;
import com.ceres.domain.entity.parameter.*;
import com.ceres.domain.entity.script.Script;
import com.ceres.service.script.ScriptService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by leonardo on 05/04/15.
 */
public class DefaultCalculatorTest {

    ScriptService scriptService;

    ScriptExecutor executor;

    DefaultFeatureCalculator defaultCalc;

    @Before
    public void init() {
        scriptService = Mockito.mock(ScriptService.class);
        Mockito.when(scriptService.getNeededScripts(null, Gender.MALE)).thenReturn(createScripts());

        executor = Mockito.mock(GroovyScriptExecutor.class);
        Mockito.when(executor.execute(Mockito.anyString(), Mockito.anyMap())).thenReturn(1.55948123);
    }

    @Test
    public void scriptCalculatorTest() {

        defaultCalc = new DefaultFeatureCalculator();
        defaultCalc.setExecutor(executor);
        defaultCalc.setScriptService(scriptService);

        Patient patient = new PatientBuilder()
                .setName(new Name("Sr.", "Teste", "Teste"))
                .setCpf("123123123")
                .setBirthdate(LocalDate.now().minusYears(20))
                .setGender(Gender.MALE)
                .build();

        Feature feature = new FeatureBuilder()
                .setVisitDate(LocalDate.now())
                .setPatient(patient)
                .addParameterValues(createBasicParameters())
                .build();

        assertEquals(3, feature.getFeatureParameters().size());

        defaultCalc.calculate(feature);

        assertEquals(6, feature.getCalcResults().size());

        assertEquals(1.559, feature.getCalcResults().get("01"), 0.001);
        assertEquals(1.559, feature.getCalcResults().get("02"), 0.001);
        assertEquals(1.559, feature.getCalcResults().get("03"), 0.001);
        assertEquals(1.559, feature.getCalcResults().get("04"), 0.001);
        assertEquals(1.559, feature.getCalcResults().get("05"), 0.001);
        assertEquals(1.559, feature.getCalcResults().get("06"), 0.001);


    }

    protected List<ParameterValue> createBasicParameters() {

        List<ParameterValue> paramValues = new ArrayList<>();

        ParameterValue paramVal = new ParameterDouble(160.0);
        paramVal.setParameter(new Parameter("altura", ParameterType.DOUBLE));
        paramValues.add(paramVal);

        paramVal = new ParameterDouble(60.00);
        paramVal.setParameter(new Parameter("peso", ParameterType.DOUBLE));
        paramValues.add(paramVal);

        paramVal = new ParameterActivityRate(ActivityRate.MODERATELY);
        paramVal.setParameter(new Parameter("taxa_de_atividade", ParameterType.ACTIVITY_RATE));
        paramValues.add(paramVal);

        return paramValues;
    }

    private static Set<Script> createScripts() {
        Script script;
        List<Script> scripts = new ArrayList<>();

        script = new Script();
        script.setMnemonic("01");
        script.setName("01");
        scripts.add(script);

        script = new Script();
        script.setMnemonic("02");
        script.setName("02");
        scripts.add(script);

        script = new Script();
        script.setMnemonic("03");
        script.setName("03");
        scripts.add(script);

        script = new Script();
        script.setMnemonic("04");
        script.setName("04");
        scripts.add(script);

        script = new Script();
        script.setMnemonic("05");
        script.setName("05");
        scripts.add(script);

        script = new Script();
        script.setMnemonic("06");
        script.setName("06");
        scripts.add(script);

        scripts.get(0).addDependency(scripts.get(1));
        scripts.get(0).addDependency(scripts.get(2));
        scripts.get(0).addDependency(scripts.get(3));

        scripts.get(1).addDependency(scripts.get(2));
        scripts.get(2).addDependency(scripts.get(3));
        scripts.get(3).addDependency(scripts.get(5));

        scripts.get(4).addDependency(scripts.get(5));

        return new HashSet<>(scripts);
    }


}
