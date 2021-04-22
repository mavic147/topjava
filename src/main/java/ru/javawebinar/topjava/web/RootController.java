package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.json.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class RootController {
    @Autowired
    private UserService userService;

    @Autowired
    private MealService mealService;

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/users")
    public String getUsers(Model model) throws IOException {
//        model.addAttribute("users", userService.getAll());
        URL url = new URL("http://localhost:8081/topjava/users");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        List<User> userList = JsonUtil.readValues(content.toString(), User.class);
        in.close();
        model.addAttribute("users", userList);
        return "users";
    }

    @PostMapping("/users")
    public String setUser(HttpServletRequest request) throws IOException {
//        int userId = Integer.parseInt(request.getParameter("userId"));
//        SecurityUtil.setAuthUserId(userId);
        URL url = new URL("http://localhost:8081/topjava/users");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        return "redirect:meals";
    }

    @GetMapping("/meals")
    public String getMeals(Model model) throws IOException {
//        model.addAttribute("meals",
//                MealsUtil.getTos(mealService.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay()));
        URL url = new URL("http://localhost:8081/topjava/meals");
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
        model.addAttribute("meals", mealsTo);
        return "meals";
    }
}
