package com.ceres.business.chart;

import com.wordnik.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * Abstração que representa uma entrada de um gráfico.
 * <p>
 * Created by leonardo on 03/05/15.
 */
@ApiModel(subTypes = {ListChartEntry.class, MapChartEntry.class, SingleValueChartEntry.class})
public abstract class ChartEntry<T> implements Serializable {

    private Object label;

    private String color;

    public abstract T getData();

    public abstract void setData(T t);

    public Object getLabel() {
        return label;
    }

    public void setLabel(Object label) {
        this.label = label;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
