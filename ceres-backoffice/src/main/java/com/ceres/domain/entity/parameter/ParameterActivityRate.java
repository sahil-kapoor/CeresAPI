package com.ceres.domain.entity.parameter;

import com.ceres.domain.entity.human.ActivityRate;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by leonardo on 21/03/15.
 */
@Entity
@DiscriminatorValue(value = "activity_rate")
@XmlRootElement(namespace = "ceres",name = "parameterActivityRate")
public class ParameterActivityRate extends ParameterValue<ActivityRate> {

    @Column(name = "ACTIVITY_RATE_VALUE")
    private ActivityRate value;

    @Override
    public ActivityRate getValue() {
        return value;
    }

    @Override
    public void setValue(ActivityRate s) {
        this.value = s;
    }

    public ParameterActivityRate() {
    }

    public ParameterActivityRate(ActivityRate activityRate) {
        this.value = activityRate;
    }

    public ParameterActivityRate(Object activityRate) {
        if (activityRate instanceof ActivityRate) {
            this.value = (ActivityRate) activityRate;
        } else {
            this.value = ActivityRate.valueOf((String) activityRate);
        }
    }

}
