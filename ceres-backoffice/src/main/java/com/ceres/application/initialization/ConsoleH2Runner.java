package com.ceres.application.initialization;

import java.sql.Connection;
import java.sql.SQLException;

import static org.h2.tools.Server.startWebServer;

/**
 * Created by leonardo on 11/02/15.
 */
public class ConsoleH2Runner implements Runnable {

    private Connection connec;

    public ConsoleH2Runner(Connection connec) {

        this.connec = connec;
    }

    @Override
    public void run() {

        try {
            startWebServer(connec);
        } catch (SQLException e) {
            throw new RuntimeException("Console H2 falhou ao iniciar...", e);
        }

    }

}
