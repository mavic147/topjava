package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<MealTo> getAllFilteredByTime(LocalTime startTime, LocalTime endTime) {
        log.info("getAllFiltered");
        return MealsUtil.getFilteredByTimeTos(service.getAll(), SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
    }

    public List<MealTo> getAllFilteredByDate(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("getAllFiltered");
        return MealsUtil.getFilteredByDateTos(service.getAll(), SecurityUtil.authUserCaloriesPerDay(), startDate, endDate);
    }

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
