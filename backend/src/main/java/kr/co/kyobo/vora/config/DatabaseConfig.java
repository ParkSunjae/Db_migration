package kr.co.kyobo.vora.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

@Slf4j
@Configuration
@MapperScan(basePackages = "kr.co.kyobo.vora.repository")
public class DatabaseConfig {
    @Value("${driverClassName}")
    String driver;
    @Value("${jdbcUrl}")
    String url;
    @Value("${db.username}")
    String username;
    @Value("${db.password}")
    String pass;
    @Value("${maximum-pool-size}")
    int max;
    @Value("${connection-timeout}")
    int timeout;
    @Value("${auto-commit}")
    Boolean autocommit;

    @Bean
    public HikariConfig hikariConfig(){
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setDriverClassName(driver);
        hikariConfig.setAutoCommit(autocommit);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(pass);
        hikariConfig.setMaximumPoolSize(max);
        hikariConfig.setConnectionTimeout(timeout);

        return hikariConfig;
    }


    @Bean
    public DataSource dataSource() throws SQLException {
        return new HikariDataSource(this.hikariConfig());
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws SQLException {
        return new DataSourceTransactionManager(this.dataSource());
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(this.dataSource());

        bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);

        return bean.getObject();
    }
}
