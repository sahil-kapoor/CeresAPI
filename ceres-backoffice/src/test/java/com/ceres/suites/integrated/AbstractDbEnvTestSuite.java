package com.ceres.suites.integrated;

import com.ceres.application.configuration.CeresSecurityContext;
import com.ceres.business.script.ScriptExecutor;
import com.ceres.domain.entity.script.Script;
import com.ceres.domain.entity.script.ScriptCategory;
import com.ceres.runner.StartCeresServer;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.internal.MapPropertiesDelegate;
import org.glassfish.jersey.server.ContainerRequest;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.ClassLoader.getSystemResourceAsStream;

/**
 * Created by leonardo on 02/02/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = StartCeresServer.class)
@TestPropertySource(value = "classpath:integrated.test.properties", inheritProperties = false)
public abstract class AbstractDbEnvTestSuite {

    @Inject
    @Qualifier("GroovyExecutor")
    protected ScriptExecutor executor;

    @Inject
    private List<JpaRepository> repoList = new ArrayList<>();

    @Before
    public void cleanUp() {
        repoList.forEach(CrudRepository::deleteAll);
    }

    protected Script createScript(String scriptFileName) {
        String scriptsource = "";
        try {
            scriptsource = IOUtils.toString(getSystemResourceAsStream(scriptFileName));
            System.out.println(scriptsource);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ScriptCategory cat = new ScriptCategory();
        cat.setName("TEST_SCRIPT");

        Script script = new Script();
        script.setName(scriptFileName);
        script.setMnemonic(scriptFileName);
        script.setCategory(cat);
        script.setScriptContent(scriptsource);

        return script;
    }

    public static ContainerRequest getFakeContainerRequest(String bUri, String rUri) {
        URI baseURI = null;
        URI requestURI = null;
        try {
            baseURI = new URI(bUri);
            requestURI = new URI(rUri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return new ContainerRequest(baseURI, requestURI, "FAKE", new CeresSecurityContext(null), new MapPropertiesDelegate());
    }

    public static ContainerRequest getFakeContainerRequest() {
        URI baseURI = null;
        URI requestURI = null;
        try {
            baseURI = new URI("base/");
            requestURI = new URI("request/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return new ContainerRequest(baseURI, requestURI, "FAKE", new CeresSecurityContext(null), new MapPropertiesDelegate());
    }

}
