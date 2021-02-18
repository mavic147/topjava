package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Filter;
import java.util.stream.Collectors;

public class MealsUtil {
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static final List<Meal> meals = Arrays.asList(
            new Meal(SecurityUtil.authUserId(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(SecurityUtil.authUserId(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(SecurityUtil.authUserId(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(SecurityUtil.authUserId(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(SecurityUtil.authUserId(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(SecurityUtil.authUserId(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(SecurityUtil.authUserId(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );

    public static List<MealTo> getTos(List<Meal> meals, int caloriesPerDay) {
        return filterByPredicateTo(meals, caloriesPerDay, meal -> true);
    }

//    public static List<MealTo> getFilteredTos(Collection<Meal> meals, int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
//        return filterByPredicate(meals, caloriesPerDay, meal -> FilterUtil.isBetweenHalfOpen(meal.get
//                , startTime, endTime));
//    }

//    public static List<Meal> getFilteredByTimeTos(Collection<Meal> meals, int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
//        return filterByPredicate(meals, caloriesPerDay, meal -> DateTimeUtil.isTimeBetweenHalfOpen(meal.getTime()
//                , startTime, endTime));
//    }
//
//    public static List<Meal> getFilteredByDateTos(Collection<Meal> meals, int caloriesPerDay, LocalDate startDate, LocalDate endDate) {
//        return filterByPredicate(meals, caloriesPerDay, meal -> DateTimeUtil.isDateBetweenHalfOpen(meal.getDate()
//                , startDate, endDate));
//    }

//    public static List<Meal> filterByPredicate(Collection<Meal> meals, int caloriesPerDay, Predicate<Meal> filter) {
//        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
//                .collect(
//                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                );
//
//        return meals.stream()
//                .filter(filter)
//                .collect(Collectors.toList());
//    }

    public static List<MealTo> filterByPredicateTo(Collection<Meal> meals, int caloriesPerDay, Predicate<Meal> filter) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(filter)
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
