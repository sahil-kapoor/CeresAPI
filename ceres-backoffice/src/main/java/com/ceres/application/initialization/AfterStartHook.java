package com.ceres.application.initialization;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Leonardo on 21/01/2015.
 */
@Component
@Profile("DEV")
public class AfterStartHook implements ApplicationListener<ContextRefreshedEvent> {

    @Inject
    private DataSource datasource;

    @Value("${open.h2.console}")
    private Boolean shouldOpenConsole;

    @Value("${server.context-path}")
    private String path;

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {

        try {
            if (shouldOpenConsole)
                openH2Console();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openH2Console() throws Exception {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new ConsoleH2Runner(datasource.getConnection()));
    }

}
