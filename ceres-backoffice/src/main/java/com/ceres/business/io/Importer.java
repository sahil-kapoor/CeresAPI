package com.ceres.business.io;

import com.ceres.domain.entity.AbstractEntity;

import java.io.File;

/**
 * Created by leonardo on 19/04/15.
 */
public interface Importer<T extends AbstractEntity> {

    T doImport(File file, Class<T> target);
}
