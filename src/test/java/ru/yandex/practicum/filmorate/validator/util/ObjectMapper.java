package ru.yandex.practicum.filmorate.validator.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;

import static ru.yandex.practicum.filmorate.validator.util.Constants.MAPPER;

public class ObjectMapper {
    public static String objMapper(Object object) throws JsonProcessingException {
        ObjectWriter ow = MAPPER.writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(object);
        return json;
    }
}
