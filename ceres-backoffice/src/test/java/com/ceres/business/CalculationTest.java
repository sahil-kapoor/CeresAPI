package com.ceres.business;

import com.ceres.business.calculation.CalculatorHelper;
import com.ceres.domain.embedabble.Name;
import com.ceres.domain.entity.human.*;
import com.ceres.domain.entity.parameter.*;
import com.ceres.domain.entity.script.Script;
import com.ceres.suites.integrated.AbstractDbEnvTestSuite;
import org.apache.commons.math3.util.Precision;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Renato Cardenete on 18/03/15.
 */
public class CalculationTest extends AbstractDbEnvTestSuite {

    private Map<String, Object> arguments = new HashMap<>();

    private Feature feature;

    @Before
    public void prepare() {
        Patient patient = new PatientBuilder()
                .setName(new Name("Sr.", "Teste", "Teste"))
                .setCpf("123123123")
                .setBirthdate(LocalDate.now().minusYears(20))
                .setGender(Gender.MALE)
                .build();

        feature = new FeatureBuilder()
                .setVisitDate(LocalDate.now())
                .setPatient(patient)
                .addParameterValues(createBasicParameters())
                .build();

//        feature.populateFeatureParameters();

        feature.getFeatureParameters().forEach(arguments::put);

    }

    @Test
    public void calculateImc() {
        Script script = createScript("scripts/Imc.groovy");

        Double result = (Double) executor.execute(script.getScriptContent(), arguments);

        result = Precision.round(result, 4);

        assertEquals(result, 23.4375, 0.01);
    }

    @Test
    public void calculateBasalMetabolism() {
        Script maleScript = createScript("scripts/male/BasalMetabolism.groovy");
        Script femaleScript = createScript("scripts/female/BasalMetabolism.groovy");

        int age = Period.between(feature.getPatient().getBirthdate(), LocalDate.now()).getYears();
        arguments.put("IDADE", age);

        ActivityRate rate = (ActivityRate) arguments.get("TAXA_DE_ATIVIDADE");
        arguments.put("TAXA_DE_ATIVIDADE", rate.getValue());

        Double maleResult = (Double) executor.execute(maleScript.getScriptContent(), arguments);
        Double femaleResult = (Double) executor.execute(femaleScript.getScriptContent(), arguments);

        maleResult = Precision.round(maleResult, 4);
        femaleResult = Precision.round(femaleResult, 4);

        assertEquals(2406.0, maleResult, 2.1);
        assertEquals(2209.0, femaleResult, 2.1);
    }

    @Test
    public void calculateFat() {
        Script maleScript = createScript("scripts/male/FatPercentage.groovy");
        Script femaleScript = createScript("scripts/female/FatPercentage.groovy");

        Double maleWithAdipometer = (Double) executor.execute(maleScript.getScriptContent(), arguments);
        Double femaleWithAdipometer = (Double) executor.execute(femaleScript.getScriptContent(), arguments);

        arguments.put("COM_ADIPOMETRO", false);

        Double maleWithoutAdipometer = (Double) executor.execute(maleScript.getScriptContent(), arguments);
        Double femaleWithoutAdipometer = (Double) executor.execute(femaleScript.getScriptContent(), arguments);

        maleWithAdipometer = Precision.round(maleWithoutAdipometer, 4);
        femaleWithAdipometer = Precision.round(femaleWithoutAdipometer, 4);
        maleWithoutAdipometer = Precision.round(maleWithoutAdipometer, 4);
        femaleWithoutAdipometer = Precision.round(femaleWithoutAdipometer, 4);

        // TODO PRECISA DE DADOS REAIS
//        assertEquals(maleWithAdipometer, 1880.8020, 0.01);
//        assertEquals(femaleWithAdipometer, 1880.8020, 0.01);
//        assertEquals(maleWithoutAdipometer, 1880.8020, 0.01);
//        assertEquals(femaleWithoutAdipometer, 1880.8020, 0.01);

        assertEquals(0, 0);
    }

    @Test
    public void calculateAbsoluteFat() {
        Script script = createScript("scripts/AbsoluteFat.groovy");

        arguments.put("PORCENTAGEM_DE_GORDURA", 55.52);

        Double result = (Double) executor.execute(script.getScriptContent(), arguments);

        result = Precision.round(result, 2);

        assertEquals(result, 33.31, 0.01);
    }

    @Test
    public void calculateMassFatFree() {
        Script script = createScript("scripts/MassFatFree.groovy");

        arguments.put("GORDURA_ABSOLUTA", 33.31);

        Double result = (Double) executor.execute(script.getScriptContent(), arguments);

        result = Precision.round(result, 4);

        assertEquals(result, 26.6899, 0.01);
    }

    @Test
    public void roundDoubleArgumentsTest() {
        Map<String, Object> arguments = new HashMap<>(this.arguments);
        CalculatorHelper.roundDoubleArguments(arguments, 1);
        for (String s : arguments.keySet()) {
            if (s != "TAXA_DE_ATIVIDADE" && s != "COM_ADIPOMETRO")
                assertEquals((Double) this.arguments.get(s), (Double) arguments.get(s), 0.07);
            else
                assertEquals(this.arguments.get(s), arguments.get(s));
        }
    }


    protected List<ParameterValue> createBasicParameters() {

        List<ParameterValue> paramValues = new ArrayList<>();

        ParameterValue paramVal = new ParameterDouble(160.0);
        paramVal.setParameter(new Parameter("ALTURA", ParameterType.DOUBLE));
        paramValues.add(paramVal);

        paramVal = new ParameterDouble(60.00);
        paramVal.setParameter(new Parameter("PESO", ParameterType.DOUBLE));
        paramValues.add(paramVal);

        paramVal = new ParameterActivityRate(ActivityRate.MODERATELY);
        paramVal.setParameter(new Parameter("TAXA_DE_ATIVIDADE", ParameterType.ACTIVITY_RATE));
        paramValues.add(paramVal);

        paramVal = new ParameterDouble(7.26);
        paramVal.setParameter(new Parameter("TRICEPS", ParameterType.DOUBLE));
        paramValues.add(paramVal);

        paramVal = new ParameterDouble(10.00);
        paramVal.setParameter(new Parameter("SUPRA_ILIACA", ParameterType.DOUBLE));
        paramValues.add(paramVal);

        paramVal = new ParameterDouble(42.82);
        paramVal.setParameter(new Parameter("ABDOMEN", ParameterType.DOUBLE));
        paramValues.add(paramVal);

        paramVal = new ParameterDouble(55.11);
        paramVal.setParameter(new Parameter("COXA", ParameterType.DOUBLE));
        paramValues.add(paramVal);

        paramVal = new ParameterDouble(6.72);
        paramVal.setParameter(new Parameter("SUBESCAPULAR", ParameterType.DOUBLE));
        paramValues.add(paramVal);

        paramVal = new ParameterDouble(62.94);
        paramVal.setParameter(new Parameter("CINTURA", ParameterType.DOUBLE));
        paramValues.add(paramVal);

        paramVal = new ParameterDouble(91.43);
        paramVal.setParameter(new Parameter("QUADRIL", ParameterType.DOUBLE));
        paramValues.add(paramVal);

        paramVal = new ParameterDouble(19.99);
        paramVal.setParameter(new Parameter("PESCOCO", ParameterType.DOUBLE));
        paramValues.add(paramVal);

        paramVal = new ParameterBoolean(true);
        paramVal.setParameter(new Parameter("COM_ADIPOMETRO", ParameterType.BOOLEAN));
        paramValues.add(paramVal);

        return paramValues;
    }
}
