package model.util;

import interceptors.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;



public class HibernateUtil {

    private static SessionFactory sessionFactory = null;
    private static ServiceRegistry serviceRegistry = null;
    private static StandardServiceRegistryBuilder serviceRegistryBuilder = null;

    //==========================================================================
    static {
        try {

            Configuration configuration = new Configuration();
            configuration.setInterceptor(new Hibernate());            
            configuration.configure();            
            serviceRegistryBuilder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());            
            serviceRegistry = serviceRegistryBuilder.build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        } catch (Exception e) {
            try {
                throw e;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    //==========================================================================
    public static void closeSession(Session session) {

        if (session != null) {

            if (session.isOpen()) {
                session.close();
            }
        }

    }

    //==========================================================================
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
} // end class
