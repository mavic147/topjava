package ru.javawebinar.topjava.repository;

import java.util.List;
import java.util.Optional;

public interface MealRepository<E> {//<E, Id>
    List<E> findAll();

//    Optional<E> findById(Id id);
//
//    void deleteById();
}
