package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;
import ru.javawebinar.topjava.web.user.ProfileRestController;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
//            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));
            ProfileRestController prc = appCtx.getBean(ProfileRestController.class);
            System.out.println(prc.getAll());
//            prc.delete(3);
//            prc.update(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN), 4);
//            prc.update(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN), 3);
//            System.out.println(prc.get(2));


//            MealRestController mrc = appCtx.getBean(MealRestController.class);
//            System.out.println(mrc.getAll());
//            System.out.println(mrc.get(3, 1));
//            System.out.println(mrc.get(3, 2));
//            mrc.get(10, 1);
//            System.out.println(mrc.create(new Meal(1, LocalDateTime.of(2021, Month.FEBRUARY, 15,  14, 0), "Обед", 650)));
//            System.out.println(mrc.getAll());
//            mrc.delete(5, 1);
//            mrc.update(new Meal(1,LocalDateTime.of(2021, Month.FEBRUARY, 15,  14, 0), "ОБЕД!!!", 800), 7, 1);
//            mrc.update(new Meal(1,LocalDateTime.of(2021, Month.FEBRUARY, 15,  14, 0), "ОБЕД!!!", 800), 7, 2);
//            mrc.update(new Meal(1,LocalDateTime.of(2021, Month.FEBRUARY, 15,  14, 0), "ОБЕД!!!", 800), 10, 1);
//            mrc.delete(5, 2);
//            mrc.delete(7, 2);
//            mrc.update(new Meal(1,LocalDateTime.of(2021, Month.FEBRUARY, 15,  14, 0), "ОБЕД!!!", 800), 5, 1);
//            System.out.println(mrc.getAll(null, null));
        }
    }
}
