package com.ceres.database;

import com.ceres.suites.integrated.AbstractDbEnvTestSuite;
import org.junit.Test;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Created by Leonardo on 25/01/2015.
 */
public class DatabaseConnectionTest extends AbstractDbEnvTestSuite {

    @Inject
    DataSource datasource;

    @Test
    public void connectionTest() {

        try {
            assertNotNull(datasource.getConnection());
        } catch (SQLException e) {
            fail("Datasource não foi iniciado corretamente...");
            e.printStackTrace();
        }

    }


}
