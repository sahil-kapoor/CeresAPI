package com.ceres.business.validation;

import javax.inject.Named;
import java.io.File;

/**
 * Created by leonardo on 02/05/15.
 */
@Named(value = "ImportValidator")
public class ImportValidator implements Validator<File> {

    private static final String FILE_TYPE = ".xml";

    @Override
    public boolean isValid(File file) {
        // TODO melhorar validação
        return file.canRead();
    }
}
