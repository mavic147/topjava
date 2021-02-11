package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private final MealDao mealDao = new MealDao();
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String action = request.getServletPath();
        String action = request.getParameter("action");

        switch(action == null ? "all" : action) {
            case "create":
                log.debug("creating a meal");
                showCreateForm(request, response);
                break;
            case "update":
                log.debug("updating a  meals");
                showUpdateForm(request, response);
                break;
            case "delete":
                log.debug("deleting a meal");
                delete(request, response);
                break;
//            case "get":
//                showMealById(request, response);
//                break;
            case "all":
                log.debug("redirect to meals");
                listMeals(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void listMeals(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setAttribute("mealsTo", filteredByStreams(mealDao.findAll(), LocalTime.MIN, LocalTime.MAX, new MealsUtil().CALORIES_PER_DAY));
        request.setAttribute("localDateTimeFormatter", dateTimeFormatter);
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher("newMealForm.jsp").forward(request,response);
    }

    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        //Здесь падает при сохранении объекта
        int id = Integer.parseInt(request.getParameter("id"));
        Meal existingMeal = mealDao.findById(id);
        request.setAttribute("meal", existingMeal);
        request.getRequestDispatcher("newMealForm.jsp").forward(request,response);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        //здесь падает при удалении объекта UnsupportedOperationException: remove
        mealDao.deleteById(id);
        response.sendRedirect("/meals.jsp");
    }

//    private void showMealById(HttpServletRequest request, HttpServletResponse response)
//            throws IOException, ServletException {
//        int id = Integer.parseInt(request.getParameter("id"));
//        request.setAttribute("meal", mealDao.findById(id));
//        request.setAttribute("localDateTimeFormatter", dateTimeFormatter);
//        request.getRequestDispatcher("mealId.jsp").forward(request, response);
//    }
}
