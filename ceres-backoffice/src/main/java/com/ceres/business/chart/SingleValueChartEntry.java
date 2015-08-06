package com.ceres.business.chart;

import com.wordnik.swagger.annotations.ApiModel;

/**
 * Created by leonardo on 03/05/15.
 */
@ApiModel(value = "Gráfico", parent = ChartEntry.class)
public class SingleValueChartEntry<T> extends ChartEntry<T> {

    private T data;

    @Override
    public T getData() {
        return data;
    }

    @Override
    public void setData(T t) {
        this.data = t;
    }
}
