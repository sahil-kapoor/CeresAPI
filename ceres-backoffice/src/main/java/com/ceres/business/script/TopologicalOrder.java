package com.ceres.business.script;

import com.ceres.domain.entity.script.Script;

import javax.inject.Named;
import java.util.*;

/**
 * Created by renato and leonardo on 31/03/2015.
 */
@Named("ScriptTopologicalOrderSorter")
public class TopologicalOrder implements ScriptOrder {


    @Override
    public Set<Script> getExecutionOrder(Script script) {
        Set<Script> allDependencies = prepareDependencies(script);
        return doTopologicalOrder(allDependencies);
    }

    @Override
    public Set<Script> getExecutionOrder(Set<Script> script) {
        Set<Script> dependencies = new HashSet<>();

        script.forEach(k -> dependencies.addAll(prepareDependencies(k)));

        return doTopologicalOrder(dependencies);
    }

    private Set<Script> prepareDependencies(Script script) {
        Set<Script> dependencies = new HashSet<>();
        dependencies.addAll(getDependencyList(script));
        return dependencies;
    }

    private Set<Script> getDependencyList(Script script) {
        final Set<Script> scriptSet = new LinkedHashSet<>();

        if (script.hasDependencies()) {
            Iterator<Script> iterator = script.getDependencies().iterator();
            while (iterator.hasNext()) {
                Script nextScript = iterator.next();
                checkCircularDependency(script, nextScript);
                scriptSet.add(script);
                scriptSet.addAll(getDependencyList(nextScript));
            }
        } else {
            scriptSet.add(script);
        }

        return scriptSet;
    }

    private void checkCircularDependency(Script source, Script dependencies) {
        for (Script s : dependencies.getDependencies())
            if (s.equals(source)) {
                throw new RuntimeException("Script com referência circular");
            }
    }

    private Set<Script> doTopologicalOrder(Collection<Script> scriptsToOrder) {
        Set<Script> ordered = new LinkedHashSet<>();

        Map<String, Script> mapAllScripts = new HashMap<String, Script>();
        Map<String, Set<String>> dependencies = new HashMap<String, Set<String>>();

        scriptsToOrder.forEach(script -> {
                    mapAllScripts.put(script.getMnemonic(), script);
                    dependencies.put(script.getMnemonic(), new HashSet<String>(script.getDependencyMnemonics()));
                }
        );

        while (dependencies.size() > 0) {
            String script = getNextScriptWithoutDependencies(dependencies);
            removeDependency(dependencies, script);
            ordered.add(mapAllScripts.get(script));
        }

        return ordered;
    }

    private String getNextScriptWithoutDependencies(Map<String, Set<String>> dependencies) {
        for (Map.Entry<String, Set<String>> entry : dependencies.entrySet()) {
            if (entry.getValue().size() == 0) {
                dependencies.remove(entry.getKey());
                return entry.getKey();
            }
        }
        throw new RuntimeException("Script com referência circular");
    }

    private void removeDependency(Map<String, Set<String>> dependencies, String script) {
        for (Set<String> dep : dependencies.values()) {
            dep.remove(script);
        }
    }
}
