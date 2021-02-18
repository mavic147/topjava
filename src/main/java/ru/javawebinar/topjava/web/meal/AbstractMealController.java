package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

public abstract class AbstractMealController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getTos(service.getAll(), SecurityUtil.authUserCaloriesPerDay());
    }

//    public List<MealTo> getAllFiltered(@Nullable LocalDate startDate, @Nullable LocalDate endDate,
//                                       @Nullable LocalTime startTime, @Nullable LocalTime endTime) {
//        List<Meal> filteredByDate = service.getAllFiltered(startDate, endDate);
//        return MealsUtil.getFilteredTos(filteredByDate, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
//    }

//    public List<Meal> getAllFilteredByTime(List<Meal> meals, LocalTime startTime, LocalTime endTime) {
//        log.info("getAllFiltered");
//        return MealsUtil.getFilteredByTimeTos(meals, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
//    }
//
//    public List<Meal> getAllFilteredByDate(List<Meal> meals, LocalDate startDate, LocalDate endDate) {
//        log.info("getAllFiltered");
//        return MealsUtil.getFilteredByDateTos(meals, SecurityUtil.authUserCaloriesPerDay(), startDate, endDate);
//    }

    public Meal get(int id, int userId) {
        log.info("get {}", id);
        return service.get(id, userId);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal);
    }

    public void update(Meal meal, int id, int userId) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal, userId);
    }

    public void delete(int id, int userId) {
        log.info("delete {}", id);
        service.delete(id, userId);
    }
}
