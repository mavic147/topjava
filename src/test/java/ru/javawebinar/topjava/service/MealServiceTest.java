package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int NOT_FOUND_ID = START_SEQ + 5;

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService mealService;

    @Test
    public void create() {
        Meal createdMeal = mealService.create(getNewMeal(), ADMIN_ID);
        Integer newId = createdMeal.getId();
        Meal newMeal = getNewMeal();
        newMeal.setId(newId);
        assertObjectsMatch(newMeal, createdMeal);
        assertObjectsMatch(newMeal, mealService.get(newId, ADMIN_ID));
    }

    @Test
    public void update() {
        Meal updatedMeal = getUpdatedMeal();
        mealService.update(updatedMeal, ADMIN_ID);
        assertObjectsMatch(mealService.get(updatedMeal.getId(), ADMIN_ID),getUpdatedMeal());
    }

    @Test
    public void updateWrongMeal() {
        assertThrows(NotFoundException.class, () -> mealService.update(getUpdatedMeal(), NOT_FOUND_ID));
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(org.springframework.dao.DuplicateKeyException.class,
                () -> mealService.create(getNewMealWithExistingDateTime(), ADMIN_ID));
    }

    @Test
    public void delete() {
        mealService.delete(1, USER_ID);
        assertThrows(NotFoundException.class, () -> mealService.get(1, USER_ID));
    }

    @Test
    public void deleteWrongMeal() {
        assertThrows(NotFoundException.class, () -> mealService.delete(2, ADMIN_ID));
    }

    @Test
    public void get() {
        assertThat(userMeal2).usingRecursiveComparison().isEqualTo(mealService.get(2, USER_ID));
    }

    @Test
    public void getWrongMeal() {
        assertThrows(NotFoundException.class, () -> mealService.get(2, ADMIN_ID));
    }

    @Test
    public void getAll() {
        assertListsMatch(actualMeals, mealService.getAll(USER_ID));
    }

    @Test
    public void getAllWrongMeals() {
        assertThat(actualMeals).usingRecursiveFieldByFieldElementComparator().isNotEqualTo(mealService.getAll(ADMIN_ID));
    }

    @Test
    public void getBetweenHalfOpen() {
        LocalDate start = LocalDate.of(2021, 2, 21);
        LocalDate end = LocalDate.of(2021, 2, 21);
        assertListsMatch(actualMeals, mealService.getBetweenInclusive(start, end, USER_ID));
    }
}