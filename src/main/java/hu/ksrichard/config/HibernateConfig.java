package hu.ksrichard.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Hibernate configuration class
 * It defines the local session factory, datasource and hibernate transaction manager
 * @author ksrichard
 */
@Configuration
@EnableTransactionManagement
@PropertySource(value = { "classpath:hibernate.properties" })
public class HibernateConfig {

    @Autowired
    private Environment environment;

    /**
     * Session Factory bean
     * @return {@link LocalSessionFactoryBean}
     */
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setHibernateProperties(hibernateProperties());
        sessionFactory.setPackagesToScan("hu.ksrichard.model");
        return sessionFactory;
    }

    /**
     * Datasource bean
     * @return {@link DataSource}
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        return dataSource;
    }

    /**
     * Construct hibernate properties for LocalSessionFactoryBean
     * @return {@link Properties}
     */
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.debug"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.debug"));
        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl"));
        return properties;
    }

    /**
     * Hibernate Transaction Manager Bean
     * @param s SessionFactory that is needed for Hibernate Transaction Manager
     * @return {@link HibernateTransactionManager}
     */
    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(s);
        return txManager;
    }

}
