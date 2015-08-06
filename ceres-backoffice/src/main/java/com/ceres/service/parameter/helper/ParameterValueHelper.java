package com.ceres.service.parameter.helper;

import com.ceres.domain.entity.human.Feature;
import com.ceres.domain.entity.parameter.*;

/**
 * Created by leonardo on 21/03/15.
 */
public final class ParameterValueHelper {

    public static ParameterValue createParameterValue(Parameter p, Object value) {
        ParameterValue resolvedParam;
        resolvedParam = getParameterValueType(p, value);
        resolvedParam.setParameter(p);
        return resolvedParam;
    }

    public static ParameterValue createParameterValue(Parameter p, Feature f, Object value) {
        ParameterValue resolvedParam;
        resolvedParam = getParameterValueType(p, value);
        resolvedParam.setFeature(f);
        resolvedParam.setParameter(p);
        return resolvedParam;
    }

    public static ParameterValue createEmptyParameterValue(Parameter p) {
        ParameterValue resolvedParam;
        resolvedParam = getParameterValueType(p);
        resolvedParam.setParameter(p);
        return resolvedParam;
    }

    public static ParameterValue cloneWithNewValue(ParameterValue param, Object value) {
        ParameterValue newParam = getParameterValueType(param.getParameter(), value);
        newParam.setFeature(param.getFeature());
        newParam.setParameter(param.getParameter());
        newParam.setId(param.getId());
        return newParam;
    }

    private static ParameterValue getParameterValueType(Parameter p, Object value) {
        ParameterValue resolvedParam;
        switch (p.getType()) {
            case STRING:
                resolvedParam = new ParameterString(value);
                break;
            case BOOLEAN:
                resolvedParam = new ParameterBoolean(value);
                break;
            case DATE:
                resolvedParam = new ParameterDate(value);
                break;
            case DOUBLE:
                resolvedParam = new ParameterDouble(value);
                break;
            case INTEGER:
                resolvedParam = new ParameterInteger(value);
                break;
            case ACTIVITY_RATE:
                resolvedParam = new ParameterActivityRate(value);
                break;
            default:
                resolvedParam = new ParameterString(value);
        }
        return resolvedParam;
    }

    private static ParameterValue getParameterValueType(Parameter p) {
        ParameterValue resolvedParam;
        switch (p.getType()) {
            case STRING:
                resolvedParam = new ParameterString();
                break;
            case BOOLEAN:
                resolvedParam = new ParameterBoolean();
                break;
            case DATE:
                resolvedParam = new ParameterDate();
                break;
            case DOUBLE:
                resolvedParam = new ParameterDouble();
                break;
            case INTEGER:
                resolvedParam = new ParameterInteger();
                break;
            case ACTIVITY_RATE:
                resolvedParam = new ParameterActivityRate();
                break;
            default:
                resolvedParam = new ParameterString();
        }
        return resolvedParam;
    }


}
