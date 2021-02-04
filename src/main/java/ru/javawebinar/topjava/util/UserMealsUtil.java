package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    static Map<LocalDate, Integer> daySumCalories = new HashMap<>();
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

//        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(11, 0), 2000));
        System.out.println(filteredByRecursion(meals, LocalTime.of(7, 0), LocalTime.of(21, 0), 2000));

    }

    /**
     * Creates a map date-dailyCalories;
     * Values from this map are compared to @caloriesPerDay parameter, according to which mealsWithExcess
     * with boolean value is created.
     */
    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> totalCaloriesPerDay = new HashMap<>();
        for (UserMeal meal : meals) {
            LocalDate key = meal.getDateTime().toLocalDate();
            totalCaloriesPerDay.put(key, totalCaloriesPerDay.getOrDefault(key, 0) + meal.getCalories());
        }

        List<UserMealWithExcess> mealsWithExcess = new ArrayList<>();
        for (UserMeal meal : meals) {
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                int consumedCalories = totalCaloriesPerDay.get(meal.getDateTime().toLocalDate());
                mealsWithExcess.add(new UserMealWithExcess(meal,consumedCalories > caloriesPerDay));
            }
        }

        return mealsWithExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> totalCaloriesPerDay = meals.stream()
                .collect(Collectors.toMap(
                        key -> key.getDateTime().toLocalDate(),
                        UserMeal::getCalories,
                        Integer::sum));

        return meals.stream().filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExcess(meal, totalCaloriesPerDay.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExcess> filteredByRecursion(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> mealsWithExcess = new ArrayList<>();
        daySumCalories.merge(meals.get(0).getDateTime().toLocalDate(), meals.get(0).getCalories(), Integer::sum);
        if (meals.size() > 1) {
            mealsWithExcess.addAll(filteredByRecursion(meals.subList(1, meals.size()), startTime, endTime, caloriesPerDay));
        }
        LocalTime time = meals.get(0).getDateTime().toLocalTime();
        if (TimeUtil.isBetweenHalfOpen(time, startTime, endTime)) {
            mealsWithExcess.add(new UserMealWithExcess(
                    meals.get(0).getDateTime(),
                    meals.get(0).getDescription(),
                    meals.get(0).getCalories(),
                    daySumCalories.get(meals.get(0).getDateTime().toLocalDate()) > caloriesPerDay)
            );
        }
        return mealsWithExcess;
    }
}
