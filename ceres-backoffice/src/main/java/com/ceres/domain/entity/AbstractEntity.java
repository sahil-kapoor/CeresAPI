package com.ceres.domain.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by leonardo on 22/03/15.
 */
@MappedSuperclass
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "jsonID")
@XmlRootElement(namespace = "ceres")
public abstract class AbstractEntity implements Serializable {

    @Id
    @TableGenerator(initialValue = 0, name = "CeresSequence")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "CeresSequence")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
