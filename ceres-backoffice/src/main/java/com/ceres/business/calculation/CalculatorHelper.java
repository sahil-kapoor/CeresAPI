package com.ceres.business.calculation;

import com.ceres.domain.entity.human.ActivityRate;
import com.ceres.domain.entity.human.Feature;
import com.ceres.domain.entity.human.Gender;
import org.apache.commons.math3.util.Precision;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by leonardo on 07/04/15.
 */
public class CalculatorHelper {

    private static final List<String> adipometerMaleParameters = new ArrayList<>(Arrays.asList("SUPRA_ILIACA", "TRICEPS", "ABDOMEN"));
    private static final List<String> adipometerFemaleParameters = new ArrayList<>(Arrays.asList("SUPRA_ILIACA", "SUBESCAPULAR", "COXA"));
    private static final String GENDER_KEY = "GENERO";
    private static final String ADIPOMETER_KEY = "COM_ADIPOMETRO";
    private static final String ACTIVITY_RATE_KEY = "TAXA_DE_ATIVIDADE";
    private static final String AGE_KEY = "IDADE";


    public static Map<String, Object> createCalcArguments(Feature feature) {
        Map<String, Object> arguments = new ConcurrentHashMap<>();

        feature.getFeatureParameters().forEach(arguments::put);

        int age = Period.between(feature.getPatient().getBirthdate(), LocalDate.now()).getYears();

        arguments.put(AGE_KEY, age);
        arguments.put(ADIPOMETER_KEY, adipometerIsAvailable(arguments));

        Double activityRate = getValueOfActivityRateString(arguments);
        feature.getFeatureParameters().put(ACTIVITY_RATE_KEY, activityRate);
        arguments.put(ACTIVITY_RATE_KEY, activityRate);

        return arguments;

    }

    public static void roundDoubleArguments(Map<String, Object> arguments, int precision) {

        arguments.forEach((k, v) -> {
            if (v instanceof Double)
                arguments.put(k, Precision.round((Double) v, precision));
        });

    }

    private static Boolean adipometerIsAvailable(Map<String, Object> arguments) {

        Gender gender = (Gender) arguments.get(GENDER_KEY);

        if (gender == Gender.MALE) {

            return arguments.keySet().containsAll(adipometerMaleParameters);

        } else {

            return arguments.keySet().containsAll(adipometerFemaleParameters);

        }

    }

    private static Double getValueOfActivityRateString(Map<String, Object> arguments) {
        ActivityRate activityRate = (ActivityRate) arguments.get(ACTIVITY_RATE_KEY);

        return activityRate.getValue();
    }


}
