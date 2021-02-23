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

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    public static final int ADMIN_ID = START_SEQ + 1;

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



}