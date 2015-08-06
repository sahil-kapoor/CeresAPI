package com.ceres.suites.unitary;

import com.ceres.controller.json.FoodJsonTest;
import junit.framework.TestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by leonardo on 02/02/15.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        FoodJsonTest.class
})
public class ControllerLayerTestSuite extends TestSuite {
}
