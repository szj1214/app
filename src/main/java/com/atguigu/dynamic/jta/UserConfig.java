package com.atguigu.dynamic.jta;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * User 数据源配置类
 *
 * @author szj
 * @since 2021-03-22 14:55
 */
@Data
@Component
@ConfigurationProperties("spring.datasource.user")
public class UserConfig {
    private String jdbcUrl;
    private String username;
    private String password;
    private String driverClassName;
    private String uniqueResourceName;
}
