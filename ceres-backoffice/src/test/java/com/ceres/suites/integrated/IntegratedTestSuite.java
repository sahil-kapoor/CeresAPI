package com.ceres.suites.integrated;

import com.ceres.database.DatabaseConnectionTest;
import com.ceres.domain.CRUDTest;
import com.ceres.domain.RepositorySanityTest;
import com.ceres.service.FoodControllerServiceImplTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by leonardo on 02/02/15.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CRUDTest.class,
        RepositorySanityTest.class,
        DatabaseConnectionTest.class,
        FoodControllerServiceImplTest.class,
})
public class IntegratedTestSuite {
}
