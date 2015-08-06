package com.ceres.business.chart;

import com.wordnik.swagger.annotations.ApiModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leonardo on 03/05/15.
 */
@ApiModel(value = "Gráfico de List", parent = ChartEntry.class)
public class ListChartEntry<T> extends ChartEntry<List<T>> {

    private List<T> data = new ArrayList<>();

    @Override
    public List<T> getData() {
        return data;
    }

    @Override
    public void setData(List<T> entries) {
        this.data = entries;
    }
}
