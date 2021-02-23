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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
        assertEquals(createdMeal, newMeal);
        assertEquals(mealService.get(newId, ADMIN_ID), newMeal);
    }

    @Test
    public void update() {
        Meal updatedMeal = mealService.create(getUpdatedMeal(), ADMIN_ID);
        Integer updatedId = updatedMeal.getId();
        Meal actualMeal = getUpdatedMeal();
        assertEquals(updatedMeal, actualMeal);
        assertEquals(mealService.get(updatedId, ADMIN_ID), actualMeal);
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
        assertEquals(mealService.get(2, USER_ID), userMeal2);
    }

    @Test
    public void getWrongMeal() {
        assertThrows(NotFoundException.class, () -> mealService.get(2, ADMIN_ID));
    }

    @Test
    public void getAll() {
        List<Meal> actualMeals = new ArrayList<>();
        actualMeals.add(userMeal1);
        actualMeals.add(userMeal2);
        actualMeals.sort(Comparator.comparing(Meal::getDateTime).reversed());
        assertThat(actualMeals).usingRecursiveFieldByFieldElementComparator().isEqualTo(mealService.getAll(100000));
    }
}