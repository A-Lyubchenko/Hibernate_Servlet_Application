package ua.lyubchenko.repositories;

import java.util.List;

public interface ICrud<T> {

    void create(T entity);

    List<T> read(Class<T> clazz);

    void update(T entity);

    void delete(T id);

}
