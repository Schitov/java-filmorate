package ru.yandex.practicum.filmorate.validator.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Constants {
    public static final String FILMSDATA = "file:src/test/java/" +
            "ru/yandex/practicum/filmorate/validator/resources/filmsData.sql";

    public static final String FILMDATA = "file:src/test/java/" +
            "ru/yandex/practicum/filmorate/validator/resources/filmData.sql";

    public static final String USERSDATA = "file:src/test/java/" +
            "ru/yandex/practicum/filmorate/validator/resources/usersData.sql";

    public static final String USERDATA = "file:src/test/java/" +
            "ru/yandex/practicum/filmorate/validator/resources/userData.sql";

    public static final String FRIENDSDATA = "file:src/test/java/" +
            "ru/yandex/practicum/filmorate/validator/resources/friendsData.sql";

    public static final String LIKESDATA = "file:src/test/java/" +
            "ru/yandex/practicum/filmorate/validator/resources/likesData.sql";

    public static final String GENRESDATA = "file:src/test/java/" +
            "ru/yandex/practicum/filmorate/validator/resources/genresData.sql";

    public static final ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules();

}
