package com.ceres.domain.entity.human;

/**
 * Created by leonardo on 10/03/15.
 */
public enum ActivityRate {

    SEDENTARY("Sedentário", 1.200),
    SLIGHTLY("Leve", 1.375),
    MODERATELY("Moderado", 1.550),
    HIGHLY("Alto", 1.725),
    EXTREMELY("Extremo", 1.900);

    private String name;
    private Double value;

    ActivityRate(String activityRate, Double value) {
        this.name = activityRate;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Double getValue() {
        return value;
    }
}
