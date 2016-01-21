package com.mm.dev.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.mm.dev.expands.mybatis.plugins.PaginationResultSetInterceptor;
import com.mm.dev.expands.mybatis.plugins.PaginationStatementInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by Lipengfei on 2015/6/26.
 */
@Configuration
@MapperScan(basePackages = "com.mm.dev.dao.mapper1", sqlSessionFactoryRef = "sqlSessionFactory1", sqlSessionTemplateRef = "sqlSessionTemplate1")
@EnableTransactionManagement
public class DataSource1Config {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource1() {
    	
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.username"));
        dataSource.setPassword(env.getProperty("jdbc.password"));
        dataSource.setInitialSize(Integer.parseInt(env.getProperty("jdbc.initialSize")));
        dataSource.setMinIdle(Integer.parseInt(env.getProperty("jdbc.minIdle")));
        dataSource.setMaxActive(Integer.parseInt(env.getProperty("jdbc.maxActive")));

        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory1(DataSource dataSource1) throws Exception {

        SqlSessionFactoryBean sessionFactory1 = new SqlSessionFactoryBean();
        // 获取properties中的对应配置信息
        String mapperPackage = env.getProperty("spring.mybatis.mapperPackage");
        String dialect = env.getProperty("spring.mybatis.dialect");

        Properties properties = new Properties();
        properties.setProperty("dialect", dialect);

        sessionFactory1.setDataSource(dataSource1);
        sessionFactory1.setConfigurationProperties(properties);
        // 设置MapperLocations路径
        if(!StringUtils.isEmpty(mapperPackage)) {
            ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            sessionFactory1.setMapperLocations(resourcePatternResolver.getResources(mapperPackage));
        }
        // 设置插件
        sessionFactory1.setPlugins(new Interceptor[] {
                new PaginationStatementInterceptor(),
                new PaginationResultSetInterceptor()
        });
        return sessionFactory1.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate1(SqlSessionFactory sqlSessionFactory1) {
        return new SqlSessionTemplate(sqlSessionFactory1);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, DataSource dataSource1) {

        LocalContainerEntityManagerFactoryBean entityManagerFactory1 = builder.dataSource(dataSource1).build();

        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");

        entityManagerFactory1.setPackagesToScan("com.mm.dev.entity.entity2");
        entityManagerFactory1.setJpaProperties(properties);

        return entityManagerFactory1;
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource1) {
        return new DataSourceTransactionManager(dataSource1);
    }

}
