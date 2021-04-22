package ru.javawebinar.topjava.web.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.io.IOException;
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

    public Set<Role> deserializeUserRoles(String jsonRoles) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CollectionType typeReference =
                TypeFactory.defaultInstance().constructCollectionType(Set.class, String.class);
        Set<String> stringRoles = mapper.readValue(jsonRoles, typeReference);
        Set<Role> roles = new HashSet<>();
        for (String role : stringRoles) {
            roles.add(Role.valueOf(role));
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
        Date registered = new Date(node.get("registered").asLong());
        Set<Role> roles = deserializeUserRoles(node.get("roles").toString());
        return new User(id, name, email, password, caloriesPerDay, registered, roles);
    }
}
