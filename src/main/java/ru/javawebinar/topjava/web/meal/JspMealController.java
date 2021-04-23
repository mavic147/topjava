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
        URL url = new URL("http://localhost:8081/topjava/meals/delete");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        Map<String,String> arguments = new HashMap<>();
        arguments.put("id", id);
        byte[] out = HttpUtil.getParamsString(arguments).getBytes(StandardCharsets.UTF_8);
        int length = out.length;
        connection.setFixedLengthStreamingMode(length);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        OutputStream os = connection.getOutputStream();
        os.write(out);
        os.flush();
        os.close();
        return "redirect:/meals";
    }

    @GetMapping("/update")
    public String update(HttpServletRequest request, Model model) throws IOException {
//        model.addAttribute("meal", super.get(getId(request)));
        String id = request.getParameter("id");
        URL url = new URL("http://localhost:8081/topjava/meals/update");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        Map<String,String> arguments = new HashMap<>();
        arguments.put("id", id);
        byte[] out = HttpUtil.getParamsString(arguments).getBytes(StandardCharsets.UTF_8);
        int length = out.length;
        connection.setFixedLengthStreamingMode(length);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        OutputStream os = connection.getOutputStream();
        os.write(out);
        os.flush();
        os.close();

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
        String dateTime = request.getParameter("dateTime");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");

        URL url = new URL("http://localhost:8081/topjava/meals/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        Map<String, String> arguments = new HashMap<>();
        arguments.put("id", id);
        arguments.put("dateTime", dateTime);
        arguments.put("description", dateTime);
        arguments.put("calories", dateTime);
        byte[] out = HttpUtil.getParamsString(arguments).getBytes(StandardCharsets.UTF_8);
        int length = out.length;
        connection.setFixedLengthStreamingMode(length);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        OutputStream os = connection.getOutputStream();
        os.write(out);
        os.flush();
        os.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        try {
            Meal meal = JsonUtil.readValue(content.toString(), Meal.class);
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
        URL url = new URL("http://localhost:8081/topjava/meals/filter");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        Map<String,String> arguments = new HashMap<>();
        arguments.put("startDate", startDate);
        arguments.put("endDate", endDate);
        arguments.put("startTime", startTime);
        arguments.put("endTime", endTime);
        byte[] out = HttpUtil.getParamsString(arguments).getBytes(StandardCharsets.UTF_8);
        int length = out.length;
        connection.setFixedLengthStreamingMode(length);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        OutputStream os = connection.getOutputStream();
        os.write(out);
        os.flush();
        os.close();

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
