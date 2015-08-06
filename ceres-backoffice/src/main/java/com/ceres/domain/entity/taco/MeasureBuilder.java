package com.ceres.domain.entity.taco;


import com.ceres.domain.Builder;

public class MeasureBuilder implements Builder<Measure> {
    private Long id = null;
    private Food food;
    private HomemadeMeasure measure;
    private Double portionQuantity;

    public MeasureBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public MeasureBuilder setFood(Food food) {
        this.food = food;
        return this;
    }

    public MeasureBuilder setMeasure(HomemadeMeasure measure) {
        this.measure = measure;
        return this;
    }

    public MeasureBuilder setPortionQuantity(Double portionQuantity) {
        this.portionQuantity = portionQuantity;
        return this;
    }

    @Override
    public Measure build() {
        return new Measure(id, food, measure, portionQuantity);
    }
}