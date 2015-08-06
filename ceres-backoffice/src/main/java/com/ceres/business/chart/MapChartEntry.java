package com.ceres.business.chart;

import com.wordnik.swagger.annotations.ApiModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by leonardo on 03/05/15.
 */
@ApiModel(value = "Gráfico de Mapa", parent = ChartEntry.class)
public class MapChartEntry<K, V> extends ChartEntry<Map<K, V>> {

    private Map<K, V> data = new HashMap<>();

    @Override
    public Map<K, V> getData() {
        return data;
    }

    @Override
    public void setData(Map<K, V> kvMap) {
        this.data = kvMap;
    }
}
