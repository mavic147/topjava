package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
@RequestMapping("/meals")
public class JspMealController {

    @Autowired
    private MealService mealService;

    @GetMapping()
    public String getAllMeals(@RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                 @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                 @RequestParam(value = "startTime", required = false) LocalTime startTime,
                                 @RequestParam(value = "endTime", required = false) LocalTime endTime, Model model) {
        int userId = SecurityUtil.authUserId();
        if (startDate == null && endDate == null && startTime == null && endTime == null) {
            model.addAttribute("meals", MealsUtil.getTos(mealService.getAll(userId), SecurityUtil.authUserCaloriesPerDay()));
        } else {
            List<Meal> mealsDateFiltered = mealService.getBetweenInclusive(startDate, endDate, userId);
            model.addAttribute("meals", MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime ,endTime));
        }
        return "meals";
    }

    @GetMapping ("/new")
    public ModelAndView newMealView() {
        return new ModelAndView("mealForm", "meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
    }

    @PostMapping()
    public String createMeal(@ModelAttribute("meal") Meal meal, BindingResult result) {
        int userId = SecurityUtil.authUserId();
        checkNew(meal);
        mealService.create(meal, userId);
        return "meals";
    }

    @GetMapping("/{id}")
    public ModelAndView updMealView(@PathVariable("id") int id) {
        int userId = SecurityUtil.authUserId();
        Meal meal = mealService.get(id, userId);
        return new ModelAndView("mealForm", "meal", meal);
    }

    @PostMapping("/{id}")
    public String updateMeal(@ModelAttribute("meal") Meal meal) {
        int userId = SecurityUtil.authUserId();
        mealService.update(meal, userId);
        return "meals";
    }

    @DeleteMapping("/{id}")
    public String deleteMeal(@PathVariable("id") int id) {
        int userId = SecurityUtil.authUserId();
        mealService.delete(id, userId);
        return "meals";
    }
}
