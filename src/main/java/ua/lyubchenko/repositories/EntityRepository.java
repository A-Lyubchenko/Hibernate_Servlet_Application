package ua.lyubchenko.repositories;

import org.hibernate.Session;
import ua.lyubchenko.connection.ApplicationConnection;


import java.util.List;


public class EntityRepository<T extends Identity> implements ICrud<T> {
    private final Session session = ApplicationConnection.getInstance();


    @Override
    public List<T> read(Class<T> clazz) {
        openSessionAndTransaction();
        List<T> resultList = session.createQuery(" FROM " + clazz.getSimpleName() + " s ORDER BY s.id", clazz).getResultList();
        session.getTransaction().commit();
        return resultList;
    }

    @Override
    public void create(T entity) {
        openSessionAndTransaction();
        session.save(entity);
        session.getTransaction().commit();
    }

    @Override
    public void delete(T entity) {
        openSessionAndTransaction();
        session.flush();
        session.clear();
        session.remove(entity);
        session.getTransaction().commit();
    }

    public void update(T entity) {
        openSessionAndTransaction();
        session.merge(entity);
        session.getTransaction().commit();
    }

    private void openSessionAndTransaction() {
        session.getSession();
        session.beginTransaction();
    }
}
