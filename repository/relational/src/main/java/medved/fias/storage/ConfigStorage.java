package medved.fias.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
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
import java.util.Properties;

/**
 * Hello world!
 *
 */
//TODO: Recognize what kind of the details should be here, but some of them will be taken away to core module
@Configuration
@ComponentScan
@EnableJpaRepositories
@PropertySource("classpath:storage.properties")
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
    @Profile("production")
    public JpaVendorAdapter jpaVendorAdapterProd(){
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(environment.getProperty("database",Database.class));
        adapter.setDatabasePlatform(environment.getProperty("databasePlatform"));
        adapter.setShowSql(false);
        adapter.setGenerateDdl(true);
        return adapter;
    }

    @Bean
    @Profile("production")
    public DataSource dataSourceExternal(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("dataSource.driver"));
        dataSource.setUrl(environment.getProperty("dataSource.url"));
        dataSource.setUsername(environment.getProperty("dataSource.user"));
        dataSource.setPassword(environment.getProperty("dataSource.password"));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSourceEmbedded, JpaVendorAdapter jpaVendorAdapter){
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSourceEmbedded);
        emf.setJpaVendorAdapter(jpaVendorAdapter);
        emf.setPackagesToScan("medved.domain");
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.hbm2ddl.auto",environment.getProperty("hibernate.hbm2ddl.auto"));
        emf.setJpaProperties(jpaProperties);
        return emf;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory localContainerEntityManagerFactoryBean){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(localContainerEntityManagerFactoryBean);
        return transactionManager;
    }
}
