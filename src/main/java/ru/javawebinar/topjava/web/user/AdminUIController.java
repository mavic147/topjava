package ru.javawebinar.topjava.web.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping(value = "/admin/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminUIController extends AbstractUserController {

    @Override
    @GetMapping
    public List<User> getAll() {
        return super.getAll();
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@RequestParam String name,
                       @RequestParam String email,
                       @RequestParam String password) {
        super.create(new User(null, name, email, password, Role.USER));
//        URL url = new URL("http://localhost:8081/topjava/users/");
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod("POST");
//        connection.setRequestProperty("Content-Type", "application/json; utf-8");
//        connection.setDoOutput(true);
//        String jsonInputString = String.format("{\"name\":\"%s\", \"email\":\"%s\", \"password\":\"%s\"}",
//                name, email, password);
//        try(OutputStream os = connection.getOutputStream()) {
//            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
//            os.write(input, 0, input.length);
//        }
//        BufferedReader in = new BufferedReader(
//                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
//        String inputLine;
//        StringBuilder content = new StringBuilder();
//        while ((inputLine = in.readLine()) != null) {
//            content.append(inputLine);
//        }
//        in.close();
//        return "redirect:users";
    }
}
