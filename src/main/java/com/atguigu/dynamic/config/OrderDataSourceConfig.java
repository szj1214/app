package com.atguigu.dynamic.config;

import com.atguigu.dynamic.jta.OrderConfig;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * Order 数据源配置类
 *
 * @author szj
 * @since 2021-03-22 14:18
 */
@MapperScan(value = "com.atguigu.dynamic.mapper.order", sqlSessionFactoryRef = "orderSqlSessionFactory")
@Configuration
public class OrderDataSourceConfig {

    /**
     * 从配置文件中获取 Order 数据源的配置信息，并注入
     *
     * @return javax.sql.DataSource
     * @author szj
     * @since 2021/3/22 12:05
     */
//    @ConfigurationProperties(prefix = "spring.datasource.order")
//    @Bean("orderDataSource")
//    public DataSource orderDataSource() {
//        return DataSourceBuilder.create().build();
//    }

    @Autowired
    private OrderConfig orderConfig;

    /**
     * 从order数据源配置类中获取配置信息，交给Atomikos管理
     *
     * @return javax.sql.DataSource
     * @author szj
     * @since 2021/3/22 14:58
     */
    @Bean("orderDataSource")
    public DataSource orderDataSource() {
        // 设置数据库连接
        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setUrl(orderConfig.getJdbcUrl());
        mysqlXADataSource.setUser(orderConfig.getUsername());
        mysqlXADataSource.setPassword(orderConfig.getPassword());
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);
        // 交给事务管理器进行管理
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setXaDataSource(mysqlXADataSource);
        atomikosDataSourceBean.setUniqueResourceName(orderConfig.getUniqueResourceName());
        atomikosDataSourceBean.setMinPoolSize(5);
        atomikosDataSourceBean.setBorrowConnectionTimeout(60);
        atomikosDataSourceBean.setMaxPoolSize(20);
        atomikosDataSourceBean.setXaDataSourceClassName(orderConfig.getDriverClassName());
        atomikosDataSourceBean.setTestQuery("SELECT 1 FROM DUAL");
        return atomikosDataSourceBean;
    }

    /**
     * 使用 Order 数据源类注入 SqlSession工厂 sqlSessionFactoryBean，并返回 SqlSessionFactory实例
     *
     * @param orderDataSource Order 数据源
     * @return org.apache.ibatis.session.SqlSessionFactory
     * @author szj
     * @since 2021/3/22 12:07
     */
    @Bean("orderSqlSessionFactory")
    public SqlSessionFactory orderSqlSessionFactoryBean(@Qualifier("orderDataSource") DataSource orderDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(orderDataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:com/atguigu/dynamic/mapper/order/*.xml"));
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setDefaultFetchSize(100);
        configuration.setDefaultStatementTimeout(30);
        sqlSessionFactoryBean.setConfiguration(configuration);
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 使用 Order 数据源替代普通数据源注入事务管理器 DataSourceTransactionManager
     *
     * @param orderDataSource Order 数据源
     * @return org.springframework.transaction.PlatformTransactionManager
     * @author szj
     * @since 2021/3/22 12:08
     */
//    @Bean(value = "orderTransactionManager")
//    public PlatformTransactionManager orderTransactionManager(@Qualifier("orderDataSource") DataSource orderDataSource) {
//        return new DataSourceTransactionManager(orderDataSource);
//    }

}
