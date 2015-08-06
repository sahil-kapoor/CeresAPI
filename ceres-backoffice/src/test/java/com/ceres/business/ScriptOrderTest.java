package com.ceres.business;

import com.ceres.business.script.TopologicalOrder;
import com.ceres.domain.entity.script.Script;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by Leonardo on 03/04/15.
 */
public class ScriptOrderTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void orderTest() {

        Script script;
        Set<Script> ordered;
        TopologicalOrder topOrder = new TopologicalOrder();

        script = scriptWithSingleDependency();
        ordered = topOrder.getExecutionOrder(script);
        assertEquals(4, ordered.size());

        script = scriptWithManyDependencies();
        ordered = topOrder.getExecutionOrder(script);
        assertEquals(5, ordered.size());

    }

    @Test
    public void complexOrderTest() {

        TopologicalOrder topOrder = new TopologicalOrder();
        Script script = createComplexOrder();

        Set<Script> ordered = topOrder.getExecutionOrder(script);
        assertEquals(10, ordered.size());

    }

    @Test
    public void simpleOrderTest() {

        TopologicalOrder topOrder = new TopologicalOrder();
        Script script = creatSimpleOrder();

        Set<Script> ordered = topOrder.getExecutionOrder(script);
        assertEquals(3, ordered.size());
    }

    @Test
    public void circularReferenceTest() {

        TopologicalOrder topOrder = new TopologicalOrder();
        Script script = createCircularReference();

        exception.expect(RuntimeException.class);
        Set<Script> ordered = topOrder.getExecutionOrder(script);

    }

    private Script scriptWithSingleDependency() {

        Script script;
        List<Script> scripts = new ArrayList<>();

        script = new Script();
        script.setMnemonic("01");
        scripts.add(script);

        script = new Script();
        script.setMnemonic("02");
        scripts.add(script);

        script = new Script();
        script.setMnemonic("03");
        scripts.add(script);

        script = new Script();
        script.setMnemonic("04");
        scripts.add(script);

        scripts.get(0).addDependency(scripts.get(1));
        scripts.get(1).addDependency(scripts.get(2));
        scripts.get(2).addDependency(scripts.get(3));

        return scripts.get(0);

    }

    private Script scriptWithManyDependencies() {

        Script script;
        List<Script> scripts = new ArrayList<>();

        script = new Script();
        script.setMnemonic("01");
        scripts.add(script);

        script = new Script();
        script.setMnemonic("02");
        scripts.add(script);

        script = new Script();
        script.setMnemonic("03");
        scripts.add(script);

        script = new Script();
        script.setMnemonic("04");
        scripts.add(script);

        script = new Script();
        script.setMnemonic("05");
        scripts.add(script);

        script = new Script();
        script.setMnemonic("06");
        scripts.add(script);

        scripts.get(0).addDependency(scripts.get(1));
        scripts.get(0).addDependency(scripts.get(2));
        scripts.get(0).addDependency(scripts.get(3));

        scripts.get(1).addDependency(scripts.get(2));
        scripts.get(2).addDependency(scripts.get(3));
        scripts.get(3).addDependency(scripts.get(5));

        scripts.get(4).addDependency(scripts.get(5));

        return scripts.get(0);

    }

    private Script createCircularReference() {
        Script script;
        List<Script> scripts = new ArrayList<>();

        script = new Script();
        script.setMnemonic("01");
        scripts.add(script);

        script = new Script();
        script.setMnemonic("02");
        scripts.add(script);


        scripts.get(0).addDependency(scripts.get(1));
        scripts.get(1).addDependency(scripts.get(0));

        return scripts.get(0);
    }

    private Script creatSimpleOrder() {

        Script script;
        List<Script> scripts = new ArrayList<>();

        script = new Script();
        script.setMnemonic("01");
        scripts.add(script);

        script = new Script();
        script.setMnemonic("02");
        scripts.add(script);

        script = new Script();
        script.setMnemonic("03");
        scripts.add(script);

        scripts.get(1).addDependency(scripts.get(0));
        scripts.get(2).addDependency(scripts.get(1));

        return scripts.get(2);

    }

    private Script createComplexOrder() {

        Script script;
        List<Script> scripts = new ArrayList<>();

        script = new Script();
        script.setMnemonic("01");
        scripts.add(script);

        script = new Script();
        script.setMnemonic("02");
        scripts.add(script);

        script = new Script();
        script.setMnemonic("03");
        scripts.add(script);

        script = new Script();
        script.setMnemonic("04");
        scripts.add(script);

        script = new Script();
        script.setMnemonic("05");
        scripts.add(script);

        script = new Script();
        script.setMnemonic("06");
        scripts.add(script);

        script = new Script();
        script.setMnemonic("07");

        scripts.add(script);
        script = new Script();
        script.setMnemonic("08");
        scripts.add(script);

        script = new Script();
        script.setMnemonic("09");
        scripts.add(script);

        script = new Script();
        script.setMnemonic("10");
        scripts.add(script);

        script = new Script();
        script.setMnemonic("11");
        scripts.add(script);


        scripts.get(1).addDependency(scripts.get(8));

        scripts.get(3).addDependency(scripts.get(0));
        scripts.get(3).addDependency(scripts.get(2));

        scripts.get(4).addDependency(scripts.get(0));
        scripts.get(5).addDependency(scripts.get(2));
        scripts.get(6).addDependency(scripts.get(4));
        scripts.get(7).addDependency(scripts.get(1));

        scripts.get(8).addDependency(scripts.get(0));
        scripts.get(8).addDependency(scripts.get(5));

        scripts.get(9).addDependency(scripts.get(4));
        scripts.get(9).addDependency(scripts.get(5));
        scripts.get(9).addDependency(scripts.get(6));
        scripts.get(9).addDependency(scripts.get(7));

        scripts.get(10).addDependency(scripts.get(9));
        scripts.get(10).addDependency(scripts.get(2));


        return scripts.get(10);

    }


}
