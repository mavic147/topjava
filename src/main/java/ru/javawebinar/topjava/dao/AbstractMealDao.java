package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface AbstractMealDao<E, Integer> {
    List<E> findAll();

    Meal findById(int id);

    void deleteById(int id);

    void update(int id, E e);

    void add(E e);
}
