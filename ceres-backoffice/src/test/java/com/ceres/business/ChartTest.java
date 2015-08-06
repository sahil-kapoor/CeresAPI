package com.ceres.business;

import com.ceres.application.configuration.ObjectMapperConfig;
import com.ceres.business.chart.ListChartEntry;
import com.ceres.business.chart.MapChartEntry;
import com.ceres.business.chart.SingleValueChartEntry;
import com.ceres.domain.entity.AbstractEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by leonardo on 03/05/15.
 */
public class ChartTest {

    private ObjectMapper mapper;

    @Before
    public void init() {
        ObjectMapperConfig config = new ObjectMapperConfig();
        mapper = config.getContext(AbstractEntity.class);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Test
    public void scriptCalculatorTest() throws JsonProcessingException {

        ListChartEntry<Double> e = new ListChartEntry<>();
        e.setLabel("Label1");
        e.setColor("Color1");
        e.setData(new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0)));

        SingleValueChartEntry<Double> e1 = new SingleValueChartEntry<>();
        e1.setLabel("Label1");
        e1.setColor("Color1");
        e1.setData(3.0);

        MapChartEntry<LocalDate, Double> e2 = new MapChartEntry<>();
        Map<LocalDate, Double> map = new HashMap<>();
        map.put(LocalDate.now(), 1.0);
        map.put(LocalDate.now().plusMonths(1), 2.0);
        map.put(LocalDate.now().plusMonths(2), 3.0);
        map.put(LocalDate.now().plusMonths(3), 4.0);
        e2.setLabel("Label1");
        e2.setColor("Color1");
        e2.setData(map);

        String json = mapper.writeValueAsString(e).replace("\"", "");
        System.out.println(json);

        json = mapper.writeValueAsString(e1).replace("\"", "");
        System.out.println(json);

        json = mapper.writeValueAsString(e2).replace("\"", "");
        System.out.println(json);


    }

}
