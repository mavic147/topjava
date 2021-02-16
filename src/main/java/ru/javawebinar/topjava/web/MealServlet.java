package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.AbstractMealController;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private AbstractMealController mealController;
    private final ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");

    @Override
    public void init() {
        mealController = appCtx.getBean(MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                Integer.parseInt(request.getParameter("userId")),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        if (meal.isNew()) {
            mealController.create(meal);
        } else {
            mealController.update(meal, Integer.parseInt(id),  SecurityUtil.authUserId());
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealController.delete(id, SecurityUtil.authUserId());
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(SecurityUtil.authUserId(), LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealController.get(getId(request), SecurityUtil.authUserId());
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                String startDate = request.getParameter("startDate");
                String endDate = request.getParameter("endDate");
                String startTime = request.getParameter("startTime");
                String endTime = request.getParameter("endTime");
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
                List<Meal> filteredMeals = new CopyOnWriteArrayList<>();
                if (startDate != null || endDate != null) {
                    //фильтрация только по дате
                    if (!startDate.equals("") || !endDate.equals("")) {
                        try {
                            filteredMeals = mealController.getAllFilteredByDate(MealsUtil.meals, LocalDate.parse(startDate, dateFormat),
                                    LocalDate.parse(endDate, dateFormat));
                        } catch(DateTimeParseException e) {
                            response.sendRedirect("error.html");
                        }
                    }

                    //фильтрация только по времени
                    if(startDate.equals("") || endDate.equals("")) {
                        if (!startTime.equals("") || !endTime.equals("")) {
                            try {
                                filteredMeals = mealController.getAllFilteredByTime(MealsUtil.meals, LocalTime.parse(startTime, timeFormat),
                                        LocalTime.parse(endTime, timeFormat));
                            } catch(DateTimeParseException e) {
                                response.sendRedirect("error.html");
                            }
                        }
                    } else if (!startTime.equals("") || !endTime.equals("")) {//фильтрация по времени и дате
                        try {
                            filteredMeals = mealController.getAllFilteredByTime(filteredMeals, LocalTime.parse(startTime, timeFormat),
                                    LocalTime.parse(endTime, timeFormat));
                        } catch(DateTimeParseException e) {
                            response.sendRedirect("error.html");
                        }
                    }

                    request.setAttribute("meals", MealsUtil.getTos(filteredMeals, SecurityUtil.authUserCaloriesPerDay()));
                } else {
                    request.setAttribute("meals", mealController.getAll());
                }
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    @Override
    public void destroy() {
        appCtx.close();
    }
}
