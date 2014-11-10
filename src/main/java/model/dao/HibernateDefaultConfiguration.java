package model.database;

import interceptors.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author skuarch
 */
public class HibernateDefaultConfiguration {

    private SessionFactory sessionFactory = null;
    private ServiceRegistry serviceRegistry = null;
    private StandardServiceRegistryBuilder serviceRegistryBuilder = null;
    private String context = null;

    public HibernateDefaultConfiguration(String context) throws HibernateException {
        this.context = context;
        initConfiguration();
    }

    //==========================================================================
    private void initConfiguration() throws HibernateException {

        Configuration configuration = new Configuration();
        configuration.setInterceptor(new Hibernate());                        
        
        configuration.setProperty(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty(AvailableSettings.DRIVER, "com.mysql.jdbc.Driver");                
        configuration.setProperty(AvailableSettings.ORDER_UPDATES, "true");
        
        configuration.setProperty(AvailableSettings.URL, "jdbc:mysql://localhost:3306/" + context);
        configuration.setProperty(AvailableSettings.USER, "swiftrecord");
        configuration.setProperty(AvailableSettings.PASS, "neutroware12");
        configuration.setProperty(AvailableSettings.C3P0_MIN_SIZE, "1");
        configuration.setProperty(AvailableSettings.C3P0_MAX_SIZE, "5");
        configuration.setProperty(AvailableSettings.C3P0_TIMEOUT, "1800");
        configuration.setProperty(AvailableSettings.C3P0_MAX_STATEMENTS, "50");                
        configuration.configure();

        serviceRegistryBuilder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        serviceRegistry = serviceRegistryBuilder.build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);

    }

    //==========================================================================
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
