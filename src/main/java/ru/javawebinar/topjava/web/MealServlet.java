package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        MealsUtil mu = new MealsUtil();
        request.setAttribute("mealsTo", filteredByStreams(mu.findAll(), LocalTime.MIN, LocalTime.MAX, mu.CALORIES_PER_DAY));
        String dateTimePattern = "yyyy-MM-dd hh:mm";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern);
        request.setAttribute("localDateTimeFormatter", dateTimeFormatter);
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }
}
