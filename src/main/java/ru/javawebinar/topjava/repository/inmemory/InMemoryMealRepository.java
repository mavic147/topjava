package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.FilterUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        meal.setId(counter.incrementAndGet());
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public Meal update(Meal meal, int userId) {
        if (meal.getUserId() == userId) {
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        } else {
            return null;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        return repository.get(id) != null && repository.get(id).getUserId() == userId && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        return repository.values().stream().filter(meal -> meal.getUserId() == userId)
                .filter(meal -> meal.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Meal> getAll() {
        return filterByPredicate(meal -> true);
    }

    @Override
    public List<Meal> getAllFiltered(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return filterByPredicate(meal -> FilterUtil.isBetweenHalfOpen(meal.getDateTime(), startDateTime, endDateTime));
    }

    public List<Meal> filterByPredicate(Predicate<Meal> filter) {
        return repository.values().stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

