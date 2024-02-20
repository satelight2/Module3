package respository;

import java.util.List;

public interface IRepository<T, K> {
    List<T> findAll(Class<T> tClass);
    T findById(K id, Class<T> tClass);
    T add(T entity);
    T edit(T entity);
    boolean remove(K id, Class<T> tClass);
}
