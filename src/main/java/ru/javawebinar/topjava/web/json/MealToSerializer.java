package ru.javawebinar.topjava.web.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ru.javawebinar.topjava.to.MealTo;

import java.io.IOException;

public class MealToSerializer extends JsonSerializer<MealTo> {

    @Override
    public void serialize(MealTo mealTo, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", mealTo.getId());
        gen.writeStringField("dateTime", mealTo.getDateTime().toString());
        gen.writeStringField("description", mealTo.getDescription());
        gen.writeBoolean(mealTo.isExcess());
        gen.writeEndObject();
    }
}
