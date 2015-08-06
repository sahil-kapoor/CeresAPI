package com.ceres.application.configuration;

import com.jolbox.bonecp.BoneCPDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

/**
 * Created by aluno on 08/04/2015.
 */
@Configuration
@Profile("BoneCP")
public class DatasourceBoneCPConfig {

    @Value("${spring.datasource.username}")
    private String jdbcUsername;

    @Value("${spring.datasource.password}")
    private String jdbcPassword;

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    @Value("${bonecp.idleMaxAgeInMinutes}")
    private Integer idleMaxAgeInMinutes;

    @Value("${bonecp.idleConnectionTestPeriodInMinutes}")
    private Integer idleConnectionTestPeriodInMinutes;

    @Value("${bonecp.maxConnectionsPerPartition}")
    private Integer maxConnectionsPerPartition;

    @Value("${bonecp.minConnectionsPerPartition}")
    private Integer minConnectionsPerPartition;

    @Value("${bonecp.partitionCount}")
    private Integer partitionCount;

    @Value("${bonecp.acquireIncrement}")
    private Integer acquireIncrement;

    @Value("${bonecp.statementsCacheSize}")
    private Integer statementsCacheSize;

    @Bean(destroyMethod = "close", name = "BoneCP")
    public DataSource dataSource() {
        BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setDriverClass(driverClassName);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(jdbcUsername);
        dataSource.setPassword(jdbcPassword);
        dataSource.setIdleConnectionTestPeriodInMinutes(idleConnectionTestPeriodInMinutes);
        dataSource.setIdleMaxAgeInMinutes(idleMaxAgeInMinutes);
        dataSource.setMaxConnectionsPerPartition(maxConnectionsPerPartition);
        dataSource.setMinConnectionsPerPartition(minConnectionsPerPartition);
        dataSource.setPartitionCount(partitionCount);
        dataSource.setAcquireIncrement(acquireIncrement);
        dataSource.setStatementsCacheSize(statementsCacheSize);
        return dataSource;
    }

}
