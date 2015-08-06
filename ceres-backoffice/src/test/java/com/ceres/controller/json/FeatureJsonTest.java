package com.ceres.controller.json;


import com.ceres.domain.entity.human.ActivityRate;
import com.ceres.domain.entity.human.Feature;
import com.ceres.domain.entity.human.FeatureBuilder;
import com.ceres.domain.entity.parameter.*;
import org.junit.Before;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Leonardo on 25/01/2015.
 */
public class FeatureJsonTest extends JsonTester {

    private static final String FEATURE_JSON = "{jsonID:1,id:1,visitDate:2014-12-12,featureParameters:{PESO:60.0,TAXA_DE_ATIVIDADE:MODERATELY,ALTURA:160.0},calcResults:{}}";
    Feature feature;

    @Before
    public void prepare() throws JAXBException {
        initializeJsonMapper();

        feature = new FeatureBuilder()
                .setVisitDate(LocalDate.of(2014, 12, 12))
                .addParameterValues(createBasicParameters())
                .build();
        feature.setId(1L);

        feature.getFeatureParameters();

    }

    private List<ParameterValue> createBasicParameters() {

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

        return paramValues;
    }

    @Override
    public void shouldSerializeToJson() throws IOException {
        String json = mapper.writeValueAsString(feature).replace("\"", "");
        assertEquals(FEATURE_JSON, json);
    }

    @Override
    public void shouldDeserializeFromJson() throws IOException {
        String json = mapper.writeValueAsString(feature);
        Feature featureDeserialized = mapper.readValue(json, Feature.class);

        assertNotNull(featureDeserialized);
        assertTrue(feature.equals(featureDeserialized));
    }


}
