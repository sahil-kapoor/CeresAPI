package com.ceres.business.io;


import com.ceres.domain.entity.AbstractEntity;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.File;

/**
 * Created by leonardo on 19/04/15.
 */
@Service(value = "GenericIOService")
public class GenericIOService implements IOService {

    private Importer<AbstractEntity> importer;

    private Exporter<AbstractEntity> exporter;

    @Inject
    public GenericIOService(Importer<AbstractEntity> importer, Exporter<AbstractEntity> exporter) {
        this.importer = importer;
        this.exporter = exporter;
    }

    @Override
    public File exportTarget(String name, Object target) {
        return exporter.doExport(name, (AbstractEntity) target);
    }

    @Override
    public Object importTarget(File file, Class<?> type) {
        return importer.doImport(file, (Class<AbstractEntity>) type);
    }
}
