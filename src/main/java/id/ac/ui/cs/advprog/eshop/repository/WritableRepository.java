package id.ac.ui.cs.advprog.eshop.repository;

import java.util.Optional;

public interface WritableRepository<T, ID> {
    T create(T entity);
    Optional<T> update(ID id, T entity);
    void delete(ID id);
}