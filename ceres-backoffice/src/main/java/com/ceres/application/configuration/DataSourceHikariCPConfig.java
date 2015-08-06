package com.ceres.application.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

/**
 * Created by aluno on 08/04/2015.
 */
@Configuration
@Profile("HikariCP")
public class DataSourceHikariCPConfig {

    @Value("${spring.datasource.username}")
    private String jdbcUser;

    @Value("${spring.datasource.password}")
    private String jdbcPassword;

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.driverClassName}")
    private String jdbcClassName;

    @Value("${hikaricp.maxLifetime}")
    private int maxLifetime;

    @Value("${hikaricp.connectionTimeout}")
    private int connectionTimeout;

    @Value("${hikaricp.idleTimeout}")
    private int idleTimeout;

    @Value("${hikaricp.maximumPoolSize}")
    private int maximumPoolSize;

    @Value("${hikaricp.poolName}")
    private String poolName;

    @Bean(destroyMethod = "close", name = "HikariCP")
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName(jdbcClassName);
        ds.setJdbcUrl(jdbcUrl);
        ds.setUsername(jdbcUser);
        ds.setPassword(jdbcPassword);
        ds.setIdleTimeout(idleTimeout);
        ds.setMaximumPoolSize(maximumPoolSize);
        ds.setPoolName(poolName);
        ds.setConnectionTimeout(connectionTimeout);
        ds.setMaxLifetime(maxLifetime);
        ds.setRegisterMbeans(true);
        return ds;
    }

}
