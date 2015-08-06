package com.ceres.service.human;

import com.ceres.domain.entity.human.Human;
import com.ceres.service.human.exception.HumanException;

import java.io.File;
import java.util.List;

/**
 * Created by leonardo on 22/03/15.
 */
public interface HumanService<T extends Human> {

    T getById(Long id);

    List<T> getAll();

    void remove(Long id);

    T update(T t) throws HumanException;

    T create(T t) throws HumanException;

    File exportToXmlFile(Long id);

    T importFromXml(File xmlFile);

    boolean exists(Long id);

}
