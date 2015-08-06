package com.ceres.business.io;

import com.ceres.domain.entity.AbstractEntity;

import java.io.File;

/**
 * Created by leonardo on 19/04/15.
 */
public interface Exporter<T extends AbstractEntity> {

    File doExport(String fileName, T t);
}
