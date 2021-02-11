package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDao implements AbstractMealDao<Meal, Integer> {
    private static AtomicInteger id = new AtomicInteger(0);


    private List<Meal> meals = Arrays.asList(
            new Meal(id.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(id.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(id.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(id.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(id.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(id.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(id.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );

    @Override
    public List<Meal> findAll() {
        return meals;
    }

//    @Override
    public Meal findById(int id) {
        return meals.stream().filter(meal -> meal.getId() == id).findAny().orElse(null);
    }

    @Override
    public void deleteById(int id) {
        meals.removeIf(meal -> meal.getId() == id);
    }

    @Override
    public void update(int id, Meal newMeal) {
        Meal mealToUpdate = findById(id);
        mealToUpdate.setDateTime(newMeal.getDateTime());
        mealToUpdate.setDescription(newMeal.getDescription());
        mealToUpdate.setCalories(newMeal.getCalories());
    }

    @Override
    public void add(Meal meal) {
        meal.setId(id.incrementAndGet());
        meals.add(meal);
    }
}
