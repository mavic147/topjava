package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController extends AbstractMealController {

    @Override
    public Meal get(int id) {
        return super.get(authUserId());
    }

    @Override
    public void update(Meal meal, int id) {
        super.update(meal, authUserId());
    }

    @Override
    public void delete(int id) {
        super.delete(authUserId());
    }
}