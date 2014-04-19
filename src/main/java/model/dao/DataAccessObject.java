package model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Data Access Object generic.
 *
 * @author skuarch
 */
public final class DataAccessObject {

    private static Session session = null;
    private static Transaction transaction = null;
    private static ServiceRegistry serviceRegistry = null;
    private static SessionFactory sessionFactory = null;

    //==========================================================================
    static {
        try {

            Configuration configuration = new Configuration();
            configuration.configure();
            serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        } catch (HibernateException e) {
            try {
                throw e;
            } catch (HibernateException ex) {
                e.printStackTrace();
            }
        }
    }

    //==========================================================================
    /**
     * this class doesn't require a constructor.
     */
    private DataAccessObject() {
    } // end DAO

    //==========================================================================
    /**
     * open a session with the database.
     */
    private static void openSession() {

        session = sessionFactory.openSession();

    } // end openSession

    //==========================================================================
    /**
     * close session.
     */
    private static void closeSession() {

        if (session != null) {

            if (session.isOpen()) {
                session.close();
            }
        }

    } // end closeSession

    //==========================================================================
    /**
     * save the object in the database.
     *
     * @param object Object
     * @return long (id)
     * @throws HibernateException
     */
    @SuppressWarnings("unchecked")
    public static long create(final Object object) throws HibernateException {

        if (object == null) {
            throw new IllegalArgumentException("the parameter object is null");
        }

        long id = 0;

        try {

            openSession();
            transaction = session.beginTransaction();
            id = (long) session.save(object);
            transaction.commit();

        } catch (HibernateException he) {
            throw he;
        } finally {
            closeSession();
        }

        return id;

    } // end create

    //==========================================================================
    /**
     * remove the object from table.
     *
     * @param object Object (Bean)
     * @throws HibernateException
     */
    public static void delete(final Object object) throws HibernateException {

        if (object == null) {
            throw new IllegalArgumentException("the parameter object is null");
        }

        try {

            openSession();
            transaction = session.beginTransaction();
            session.delete(object);
            transaction.commit();

        } catch (HibernateException he) {
            throw he;
        } finally {
            closeSession();
        }

    } // end delete 

    //==========================================================================
    /**
     * return a Object from table.
     *
     * @param <T> type
     * @param id long
     * @param type type
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(final long id, final T type) {

        if (type == null) {
            throw new IllegalArgumentException("the parameter type is null");
        }

        if (id < 1) {
            throw new IllegalArgumentException("the parameter id is less than 1");
        }

        T t = null;

        try {

            openSession();
            transaction = session.beginTransaction();
            t = (T) session.get(type.getClass(), id);
            transaction.commit();

        } catch (HibernateException he) {
            throw he;
        } finally {
            closeSession();
        }

        return t;

    } // end get

    //==========================================================================
    /**
     * return a List of specific objects.
     *
     * @param <T> type
     * @param type type
     * @return List<T>
     * @throws HibernateException
     */
    public static <T> List<T> getList(final T type) throws HibernateException {

        if (type == null) {
            throw new IllegalArgumentException("the parameter type is null");
        }

        List<T> list = null;

        try {

            openSession();
            transaction = session.beginTransaction();
            list = session.createQuery("from " + type.getClass().getCanonicalName()).list();
            transaction.commit();

        } catch (HibernateException he) {
            throw he;
        } finally {
            closeSession();
        }

        return list;

    } // end getList

    //==========================================================================
    /**
     * execute a <code>hql</code> query in the database,.
     *
     * @param <T> type
     * @param hql String
     * @param type type
     * @return List<T>
     */
    public static <T> List<T> hql(final String hql, final T type) {

        if (hql == null || hql.length() < 1) {
            throw new IllegalArgumentException("the parameter hql is null");
        }

        if (type == null) {
            throw new IllegalArgumentException("the parameter type is null");
        }

        List<T> list = null;
        Query query = null;

        try {

            openSession();
            query = session.createQuery(hql);
            query.setProperties(type);
            list = query.list();

        } catch (HibernateException e) {
            throw e;
        } finally {
            closeSession();
        }

        return list;

    } // end hql

    //==========================================================================
    /**
     * execute a <code>hql</code> sentence.
     *
     * @param <T> type
     * @param hql String
     * @param type type
     * @param maxResults int
     * @return List<T>
     */
    public static <T> List<T> hql(final String hql, final T type, final int maxResults) {

        if (hql == null || hql.length() < 1) {
            throw new IllegalArgumentException("the parameter hql is null");
        }

        if (type == null) {
            throw new IllegalArgumentException("the parameter type is null");
        }

        if (maxResults < 1) {
            throw new IllegalArgumentException("the parameter maxResults is incorrect");
        }

        List<T> list = null;
        Query query = null;

        try {

            openSession();
            query = session.createQuery(hql);
            query.setMaxResults(maxResults);
            query.setProperties(type);
            list = query.list();

        } catch (HibernateException e) {
            throw e;
        } finally {
            closeSession();
        }

        return list;

    } // end hql

    //==========================================================================
    /**
     * retrieve a query from bean and execute the sentence.
     *
     * @param <T> type
     * @param queryName String
     * @param type type
     * @return List<T>
     * @throws HibernateException
     */
    public static <T> List<T> query(final String queryName, final T type) throws HibernateException {

        if (queryName == null || queryName.length() < 1) {
            throw new NullPointerException("queryName is null or empty");
        }

        if (type == null) {
            throw new NullPointerException("type is null");
        }

        Query query = null;
        List<T> list = null;

        try {

            openSession();
            query = session.getNamedQuery(queryName);
            query.setProperties(type);
            list = query.list();

        } catch (HibernateException he) {
            throw he;
        } finally {
            closeSession();
        }

        return list;

    } // end query

    //==========================================================================
    /**
     * retrieve a query from bean and execute the sentence with parameters.
     *
     * @param <T> type
     * @param queryName String
     * @param parameters HashMap
     * @param type type
     * @return List<T>
     * @throws HibernateException
     */
    public static <T> List<T> query(final String queryName, final HashMap<String, String> parameters, final T type) throws HibernateException {

        if (queryName == null || queryName.length() < 1) {
            throw new NullPointerException("queryName is null or empty");
        }

        if (parameters == null || parameters.size() < 1) {
            throw new NullPointerException("parameters are null or empty");
        }

        if (type == null) {
            throw new NullPointerException("type is null");
        }

        Query query = null;
        List<T> list = null;

        try {

            openSession();
            query = session.getNamedQuery(queryName);
            query.setProperties(type);

            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                query.setString(entry.getKey(), entry.getValue());
            }

            list = query.list();

        } catch (HibernateException he) {
            throw he;
        } finally {
            closeSession();
        }

        return list;

    } // end query

    //==========================================================================
    /**
     * retrieve a query from bean and execute the sentence with parameters and
     * max result.
     *
     * @param <T> type
     * @param queryName String
     * @param parameters HashMap
     * @param type type
     * @param maxResults int
     * @return List<T>
     * @throws HibernateException
     */
    public static <T> List<T> query(final String queryName, final HashMap<String, String> parameters, final T type, final int maxResults) throws HibernateException {

        if (queryName == null || queryName.length() < 1) {
            throw new NullPointerException("queryName is null or empty");
        }

        if (parameters == null || parameters.size() < 1) {
            throw new NullPointerException("parameters are null or empty");
        }

        if (type == null) {
            throw new NullPointerException("type is null");
        }

        if (maxResults < 1) {
            throw new IllegalArgumentException("the parameter maxResults is incorrect");
        }

        String key = null;
        String value = null;
        Query query = null;
        List<T> list = null;

        try {

            openSession();
            query = session.getNamedQuery(queryName);
            query.setProperties(type);
            query.setMaxResults(maxResults);

            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                key = entry.getKey();
                value = entry.getValue();
                query.setString(key, value);
            }

            list = query.list();

        } catch (HibernateException he) {
            throw he;
        } finally {
            closeSession();
        }

        return list;

    } // end query
    
    //==========================================================================
    /**
     * update a object in database.
     *
     * @param object Object
     * @throws HibernateException
     */
    public static void update(final Object object) throws HibernateException {

        if (object == null) {
            throw new IllegalArgumentException("the parameter object is null");
        }

        try {

            openSession();
            transaction = session.beginTransaction();
            session.update(object);
            transaction.commit();

        } catch (HibernateException he) {
            throw he;
        } finally {
            closeSession();
        }

    } // end update

} // end class