//package com.muggle.datasource.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import javax.sql.DataSource;
//
//
///**
// * Description
// * Date 2023/10/27
// * Created by muggle
// */
//public class DatasourceConfig {
//
//    private static final Logger logger = LoggerFactory.getLogger(DatasourceConfig.class);
//
//    @Bean(name = "masterDataSource")
//    @Primary
//    @ConfigurationProperties(prefix = "spring.datasource.master")
//    public DataSource primaryDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "slaveDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.slave")
//    public DataSource secondaryDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//}
