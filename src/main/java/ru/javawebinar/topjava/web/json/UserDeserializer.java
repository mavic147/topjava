package ru.javawebinar.topjava.web.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class UserDeserializer extends StdDeserializer<User> {

    public UserDeserializer() {
        this(null);
    }

    public UserDeserializer(Class<?> vc) {
        super(vc);
    }

    public Set<Role> deserializeUserRoles(JsonParser jp) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        ObjectMapper mapper = new ObjectMapper();
        String jsonRoles = node.get("roles").asText();
        String[] stringRoles = mapper.readValue(jsonRoles, String[].class);
        Set<String> setStringRoles = Set.of(stringRoles);
        Set<Role> roles = new HashSet<>();
        for (String role : setStringRoles) {
            Role roleEnum = Role.valueOf(role);
            roles.add(roleEnum);
        }
        return roles;
    }

    @Override
    public User deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        int id = (Integer) ((IntNode) node.get("id")).numberValue();
        String name = node.get("name").asText();
        String email = node.get("email").asText();
        String password = node.get("password").asText();
        int caloriesPerDay = (Integer) ((IntNode) node.get("caloriesPerDay")).numberValue();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        Date registered = null;
        try {
            registered = formatter.parse(node.get("registered").asText());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Set<Role> roles = deserializeUserRoles(jp);
        return new User(id, name, email, password, caloriesPerDay, registered, roles);
    }
}
