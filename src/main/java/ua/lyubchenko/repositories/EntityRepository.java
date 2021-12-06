package ua.lyubchenko.repositories;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ua.lyubchenko.connection.ApplicationConnection;


import java.util.List;


public class EntityRepository<T extends Identity> implements ICrud<T> {
    private final Session session = ApplicationConnection.getInstance();


    @Override
    public List<T> read(Class<T> clazz) {
        Transaction transaction = getTransaction();
        List<T> resultList = null;
        try {

            resultList = session.createQuery(" FROM " + clazz.getSimpleName() + " s ORDER BY s.id", clazz).getResultList();
            session.getTransaction();
            transaction.commit();


        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return resultList;
    }

    @Override
    public void create(T entity) {
        Transaction transaction = getTransaction();
        try {
            session.persist(entity);
            session.getTransaction();
            transaction.commit();


        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

    }

    @Override
    public void delete(T entity) {
        Transaction transaction = getTransaction();
        try {
            session.flush();
            session.clear();
            session.remove(entity);
            session.getTransaction();
            transaction.commit();


        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public T getById(Class<T> clazz, Long id) {
        Transaction transaction = getTransaction();
        T entity = null;
        try {
            entity = session.get(clazz, id);
            session.getTransaction();
            transaction.commit();


        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return entity;
    }

    public void update(T entity) {
        Transaction transaction = getTransaction();
        try {
            session.merge(entity);
            session.getTransaction();
            transaction.commit();


        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }


    }

    private Transaction getTransaction() {
        session.getSession();
        return session.beginTransaction();
    }
}
