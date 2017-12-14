package com.clustertech.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.clustertech.datasource.properties.MySqlDataSourceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MySqlDataSourceConfig {
    @Autowired
    private MySqlDataSourceProperties dataSourceProperties;

    @Bean
    public DruidDataSource dataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(dataSourceProperties.getUrl());
        druidDataSource.setUsername(dataSourceProperties.getUsername());
        druidDataSource.setPassword(dataSourceProperties.getPassword());
        return druidDataSource;
    }
}
