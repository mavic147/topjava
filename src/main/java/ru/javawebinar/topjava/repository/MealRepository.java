package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MealRepository {
    // null if updated meal do not belong to userId
    Meal save(Meal meal);

    // false if meal do not belong to userId
    boolean delete(int id);

    // null if meal do not belong to userId
    Optional<Meal> get(int id);

    // ORDERED dateTime desc
    List<Meal> getAll();
}
