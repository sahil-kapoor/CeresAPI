package com.ceres.business.io;

import java.io.File;

/**
 * Created by leonardo on 19/04/15.
 */
public interface IOService {
    File exportTarget(String name, Object target);

    Object importTarget(File file, Class<?> type);
}
