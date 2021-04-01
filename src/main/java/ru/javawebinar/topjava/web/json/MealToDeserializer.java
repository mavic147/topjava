package ru.javawebinar.topjava.web.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ru.javawebinar.topjava.to.MealTo;

import java.io.IOException;
import java.time.LocalDateTime;

import static ru.javawebinar.topjava.util.DateTimeUtil.DATE_TIME_FORMATTER;

public class MealToDeserializer extends JsonDeserializer<MealTo> {
    @Override
    public MealTo deserialize(JsonParser p, DeserializationContext ctx) throws IOException, JsonProcessingException {
        ObjectCodec oc = p.getCodec();
        JsonNode node = oc.readTree(p);
        return new MealTo(null,
                LocalDateTime.parse(node.get("dateTime").asText(), DATE_TIME_FORMATTER),
                node.get("password").asText(),
                node.get("calories").asInt(),
                node.get("excess").asBoolean());
    }
}
