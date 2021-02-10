package ru.javawebinar.topjava.dao;

import java.util.List;
import java.util.Optional;

public interface AbstractMealDao<E, Integer> {
    List<E> findAll();

    Optional<E> findById(int id);

    void deleteById();
}
