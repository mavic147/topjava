package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.getUserId() == SecurityUtil.authUserId()) {
            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
                repository.put(meal.getId(), meal);
                return meal;
            }
            // handle case: update, but not present in storage
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        } else {
            return null;
        }
    }

    @Override
    public boolean delete(int id) {
        return repository.get(id).getUserId() == SecurityUtil.authUserId() && repository.remove(id) != null;
    }

    @Override
    public Optional<Meal> get(int id) {
        return repository.values().stream().filter(meal -> meal.getUserId() == SecurityUtil.authUserId())
                .filter(meal -> meal.getId() == id).findFirst();
    }

    @Override
    public List<Meal> getAll() {
        return repository.values().stream().filter(meal -> meal.getUserId() == SecurityUtil.authUserId())
                .sorted((prevMeal, curMeal) -> curMeal.getDateTime().compareTo(prevMeal.getDateTime()))
                .collect(Collectors.toList());
    }
}

