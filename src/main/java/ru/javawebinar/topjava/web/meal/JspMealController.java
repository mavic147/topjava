package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.HttpUtil;
import ru.javawebinar.topjava.web.json.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractMealController {

    @GetMapping("/delete")
    public String delete(HttpServletRequest request) throws IOException {
//        super.delete(getId(request));
        String id = request.getParameter("id");
        String dynamicURL = String.format("http://localhost:8081/topjava/meals/delete?id=%s", id);
        URL url = new URL(dynamicURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return "redirect:/meals";
    }

    @GetMapping("/update")
    public String update(HttpServletRequest request, Model model) throws IOException {
//        model.addAttribute("meal", super.get(getId(request)));
        String id = request.getParameter("id");
        String dynamicURL = String.format("http://localhost:8081/topjava/meals/update?id=%s", id);
        URL url = new URL(dynamicURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        Meal meal = JsonUtil.readValue(content.toString(), Meal.class);
        in.close();
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/create")
    public String create(Model model) throws IOException {
//        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), "", 1000));
        URL url = new URL("http://localhost:8081/topjava/meals/create");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        Meal meal = JsonUtil.readValue(content.toString(), Meal.class);
        in.close();
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping
    public String updateOrCreate(HttpServletRequest request) throws IOException {
//        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
//                request.getParameter("description"),
//                Integer.parseInt(request.getParameter("calories")));
//
//        if (request.getParameter("id").isEmpty()) {
//            super.create(meal);
//        } else {
//            super.update(meal, getId(request));
//        }

        String id = request.getParameter("id");
        long dateTime = LocalDateTime.parse(request.getParameter("dateTime")).toEpochSecond(ZoneOffset.UTC);
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");
        URL url = new URL("http://localhost:8081/topjava/meals/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setDoOutput(true);
        String jsonInputString = String.format("{\"id\":\"%s\", \"dateTime\":\"%s\", \"description\":\"%s\", " +
                "\"calories\":\"%s\"}", id, dateTime, description, calories);
        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        try {
            JsonUtil.readValue(content.toString(), Meal.class);
        } catch (IllegalArgumentException e) {

        } finally {
            return "redirect:/meals";
        }
    }

    @GetMapping("/filter")
    public String getBetween(HttpServletRequest request, Model model) throws IOException {
//        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
//        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
//        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
//        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
//        model.addAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));

        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String dynamicURL = String.format("http://localhost:8081/topjava/meals/filter?startDate=%s&endDate=%s&startTime=%s&endTime=%s",
                startDate, endDate, startTime, endTime);
        URL url = new URL(dynamicURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        List<MealTo> mealsTo = JsonUtil.readValues(content.toString(), MealTo.class);
        in.close();
        model.addAttribute("meals", mealsTo);
        return "meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
