package id.ac.ui.cs.advprog.eshop.repository;

import java.util.Iterator;
import java.util.Optional;

public interface ReadonlyRepository<T, ID> {
    Optional<T> findById(ID id);
    Iterator<T> findAll();
}