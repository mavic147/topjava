package ru.javawebinar.topjava.web.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import ru.javawebinar.topjava.to.MealTo;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class MealToDeserializer extends StdDeserializer<MealTo> {

    public MealToDeserializer() {
        this(null);
    }

    public MealToDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public MealTo deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        int id = (Integer) ((IntNode) node.get("id")).numberValue();
        LocalDateTime dateTime = LocalDateTime.ofEpochSecond(node.get("dateTime").asLong(), 0, ZoneOffset.UTC);
        String description = node.get("description").asText();
        int calories = (Integer) ((IntNode) node.get("calories")).numberValue();
        boolean excess = node.get("excess").asBoolean();
        return new MealTo(id, dateTime,description,calories,excess);
    }
}
