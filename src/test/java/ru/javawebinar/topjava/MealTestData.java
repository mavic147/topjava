package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

public class MealTestData {
    public static final Meal userMeal1 = new Meal(1, LocalDateTime.of(2021, Month.FEBRUARY, 21, 10, 15, 17),
            "Breakfast", 570);

    public static final Meal userMeal2 = new Meal(2, LocalDateTime.of(2021, Month.FEBRUARY, 21, 17, 26, 19),
            "Kebob", 1256);

    public static final Meal adminMeal = new Meal(3, LocalDateTime.of(2021, Month.FEBRUARY, 20, 19, 38, 21),
            "Youghurt", 400);

    //эталонный meal
    public static Meal getNewMeal() {
        return new Meal(null, LocalDateTime.of(2021, Month.FEBRUARY, 22, 16, 34, 19), "Snack", 699);
    }

    //эталонный обновленный meal
    public static Meal getUpdatedMeal() {
        Meal updated = new Meal(adminMeal);
        updated.setDateTime(LocalDateTime.of(2021, Month.FEBRUARY, 19, 20, 11, 15));
        updated.setDescription("Sandwich");
        updated.setCalories(749);
        return updated;
    }
}