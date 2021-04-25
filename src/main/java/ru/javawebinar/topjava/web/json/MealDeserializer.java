package ru.javawebinar.topjava.web.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.javawebinar.topjava.model.Meal;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class MealDeserializer extends StdDeserializer<Meal> {

    public MealDeserializer() {
        this(null);
    }

    public MealDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Meal deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        int id = (Integer) node.get("id").numberValue();
        LocalDateTime dateTime = LocalDateTime.ofEpochSecond(node.get("dateTime").asLong(), 0, ZoneOffset.UTC);
        String description = node.get("description").asText();
        int calories = (Integer) node.get("calories").numberValue();
        int userId = (Integer) node.get("userId").numberValue();
        return new Meal(id, dateTime, description, calories, userId);
    }
}
