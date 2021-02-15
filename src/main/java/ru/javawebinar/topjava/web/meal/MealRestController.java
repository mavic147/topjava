package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController extends AbstractMealController {

    @Override
    public Meal get(int id, int userId) {
        return super.get(id, authUserId());
    }

    @Override
    public void update(Meal meal, int id, int userId) {
        super.update(meal, id, authUserId());
    }

    @Override
    public void delete(int id, int userId) {
        super.delete(id, authUserId());
    }
}