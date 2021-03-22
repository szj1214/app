package com.atguigu.dynamic.config;

import com.atguigu.dynamic.jta.UserConfig;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * User 数据源配置类
 *
 * @author szj
 * @since 2021-03-22 14:17
 */
@MapperScan(value = "com.atguigu.dynamic.mapper.user", sqlSessionFactoryRef = "userSqlSessionFactory")
@Configuration
public class UserDataSourceConfig {

    /**
     * 从配置文件中获取 User 数据源的配置信息，并注入
     *
     * @return javax.sql.DataSource
     * @author szj
     * @since 2021/3/22 12:04
     */
//    @Primary
//    @ConfigurationProperties(prefix = "spring.datasource.user")
//    @Bean("userDataSource")
//    public DataSource userDataSource() {
//        return DataSourceBuilder.create().build();
//    }

    @Autowired
    UserConfig userConfig;

    /**
     * 从user数据源配置类中获取配置信息，交给Atomikos管理
     *
     * @return javax.sql.DataSource
     * @author szj
     * @since 2021/3/22 14:58
     */
    @Bean("userDataSource")
    public DataSource userDataSource() {
        // 设置数据库连接
        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setUrl(userConfig.getJdbcUrl());
        mysqlXADataSource.setUser(userConfig.getUsername());
        mysqlXADataSource.setPassword(userConfig.getPassword());
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);
        // 交给事务管理器进行管理
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setXaDataSource(mysqlXADataSource);
        atomikosDataSourceBean.setUniqueResourceName(userConfig.getUniqueResourceName());
        atomikosDataSourceBean.setMinPoolSize(5);
        atomikosDataSourceBean.setBorrowConnectionTimeout(60);
        atomikosDataSourceBean.setMaxPoolSize(20);
        atomikosDataSourceBean.setXaDataSourceClassName(userConfig.getDriverClassName());
        atomikosDataSourceBean.setTestQuery("SELECT 1 FROM DUAL");
        return atomikosDataSourceBean;
    }

    /**
     * 使用User 数据源类注入 SqlSession工厂 sqlSessionFactoryBean，并返回 SqlSessionFactory实例
     *
     * @param userDataSource User 数据源
     * @return org.apache.ibatis.session.SqlSessionFactory
     * @author szj
     * @since 2021/3/22 12:07
     */
    @Primary
    @Bean("userSqlSessionFactory")
    public SqlSessionFactory userSqlSessionFactoryBean(@Qualifier("userDataSource") DataSource userDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(userDataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:com/atguigu/dynamic/mapper/user/*.xml"));
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setDefaultFetchSize(100);
        configuration.setDefaultStatementTimeout(30);
        sqlSessionFactoryBean.setConfiguration(configuration);
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 使用动态数据源替代普通数据源注入事务管理器 DataSourceTransactionManager
     *
     * @param userDataSource User 数据源
     * @return org.springframework.transaction.PlatformTransactionManager
     * @author szj
     * @since 2021/3/22 12:08
     */
//    @Primary
//    @Bean(value = "userTransactionManager")
//    public PlatformTransactionManager userTransactionManager(@Qualifier("userDataSource") DataSource userDataSource) {
//        return new DataSourceTransactionManager(userDataSource);
//    }

}
