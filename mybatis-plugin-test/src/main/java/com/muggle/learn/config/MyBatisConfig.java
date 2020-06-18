package com.muggle.learn.config;

import com.github.pagehelper.PageInterceptor;
import com.muggle.learn.plugin.PagePlugin;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @Description:
 * @Author: muggle
 * @Date: 2020/4/27
 **/

@Configuration
public class MyBatisConfig {
    private static final Logger log = LoggerFactory.getLogger(MyBatisConfig.class);
    @Autowired
    private DataSource dataSource;




    @Bean
    public SqlSessionFactory sqlSessionFactory() {

        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();

        bean.setDataSource(dataSource);

        //分页插件
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        pageInterceptor.setProperties(properties);






        //添加插件

        PagePlugin pagePlugin = new PagePlugin();
        bean.setPlugins();

        try {

            //指定基包

            bean.setTypeAliasesPackage("com.muggle.learn.mapper");

            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/**/*.xml"));

            return bean.getObject();

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        }

    }


    @Bean
    @Autowired
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {

        return new SqlSessionTemplate(sqlSessionFactory);

    }



    @Bean

    public DataSourceTransactionManager transactionManager(DataSource dataSource) {

        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();

        dataSourceTransactionManager.setDataSource(dataSource);

        return dataSourceTransactionManager;

    }



}
