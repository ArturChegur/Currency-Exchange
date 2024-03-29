package service;

import java.util.List;

public interface Service<T, E> {
    List<T> findAll();

    T findByCode(E entity);

    void add(E entity);
}