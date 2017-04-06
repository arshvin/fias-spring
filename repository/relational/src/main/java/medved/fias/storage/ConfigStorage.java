package medved.fias.storage;

import com.google.common.collect.FluentIterable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.*;

/**
 * Hello world!
 *
 */
@Configuration
@ComponentScan
@EnableJpaRepositories
@PropertySource(value = "classpath:storage.properties", ignoreResourceNotFound = true)
public class ConfigStorage
{
    @Autowired
    Environment environment;

    @Bean
    @Profile("develop")
    public JpaVendorAdapter jpaVendorAdapterDev(){
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.H2);
        adapter.setDatabasePlatform("org.hibernate.dialect.H2Dialect");
        adapter.setShowSql(false);
        adapter.setGenerateDdl(true);
        return adapter;
    }

    @Bean
    @Profile("develop")
    public DataSource dataSourceEmbedded(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:test_fias;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    @Profile("prod")
    public JpaVendorAdapter jpaVendorAdapterProd(){
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(environment.getProperty("database",Database.class));
        adapter.setDatabasePlatform(environment.getProperty("databasePlatform"));
        adapter.setShowSql(false);
        adapter.setGenerateDdl(true);
        return adapter;
    }

    @Bean
    @Profile("prod")
    public DataSource dataSourceExternal(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("dataSource.driver"));
        dataSource.setUrl(environment.getProperty("dataSource.url"));
        dataSource.setUsername(environment.getProperty("dataSource.user"));
        dataSource.setPassword(environment.getProperty("dataSource.password"));
        return dataSource;
    }

    @Bean
    @Profile({"develop", "prod"})
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter){
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setJpaVendorAdapter(jpaVendorAdapter);
        emf.setPackagesToScan("medved.fias.storage.domain");
        Properties jpaProperties = new Properties();

        ApplicationContext applicationContext = new GenericApplicationContext();
        Environment env = applicationContext.getEnvironment();
        List activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains("develop")){
            jpaProperties.put("hibernate.hbm2ddl.auto","create");
        } else {
            jpaProperties.put("hibernate.hbm2ddl.auto",environment.getProperty("hibernate.hbm2ddl.auto"));
        }

        emf.setJpaProperties(jpaProperties);
        return emf;
    }

    @Bean
    @Profile({"develop", "prod"})
    public JpaTransactionManager transactionManager(EntityManagerFactory localContainerEntityManagerFactoryBean){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(localContainerEntityManagerFactoryBean);
        return transactionManager;
    }
}
