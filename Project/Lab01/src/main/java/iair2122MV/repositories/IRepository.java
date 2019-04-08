package iair2122MV.repositories;

import java.util.List;

public interface IRepository<T> {

    boolean add(T entity);
    boolean remove(T entity);
    boolean save();
    int count();
    List<T> getByName(String name);
    List<T> getAll();

}
