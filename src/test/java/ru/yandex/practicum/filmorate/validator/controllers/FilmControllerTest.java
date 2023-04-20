package ru.yandex.practicum.filmorate.validator.controllers;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.yandex.practicum.filmorate.model.Film;

import javax.transaction.Transactional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.yandex.practicum.filmorate.validator.util.Constants.*;
import static ru.yandex.practicum.filmorate.validator.util.FillData.getFilm;
import static ru.yandex.practicum.filmorate.validator.util.ObjectMapper.objMapper;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql(value = FILMSDATA, executionPhase = BEFORE_TEST_METHOD)
    @Transactional
    void shouldGetFilms() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/films"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name").value("A Space Odyssey"))
                .andExpect(jsonPath("$[0].description").value("Both ‘Interstellar’\n" +
                        "and ‘Gravity’ took us out of this world, but the reputation of\n" +
                        "Stanley Kubrick’s classic – now re-released – is safe. "))
                .andExpect(jsonPath("$[0].duration").value(139))
                .andExpect(jsonPath("$[0].rate").value(1))
                .andExpect(jsonPath("$[2].name").value("The Godfather"))
                .andExpect(jsonPath("$[2].description").value("The Godfather is a 1972 American crime film[2]\n" +
                        "directed by Francis Ford Coppola, who co-wrote the screenplay with Mario Puzo,\n" +
                        "based on Puzos best-selling 1969 novel of the same title. "))
                .andExpect(jsonPath("$[2].duration").value(175))
                .andExpect(jsonPath("$[2].rate").value(1));
    }

    @Test
    @Transactional
    void shouldCreateFilm() throws Exception {
        Film film = getFilm();
        String jsonFilm = objMapper(film);

        this.mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonFilm))
                .andDo(print())
                .andExpect(jsonPath("name").value("New film"));
    }

    @Test
    @Sql(value = FILMDATA, executionPhase = BEFORE_TEST_METHOD)
    @Transactional
    void shouldUpdateFilm() throws Exception {
        Film film = getFilm();
        String jsonFilm = objMapper(film);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonFilm))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name").value("New film"));
    }

    @Test
    @Sql(value = FILMSDATA, executionPhase = BEFORE_TEST_METHOD)
    @Transactional
    void shouldGetFilmById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/films/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name").value("A Space Odyssey"))
                .andExpect(jsonPath("description").value("Both ‘Interstellar’\n" +
                        "and ‘Gravity’ took us out of this world, but the reputation of\n" +
                        "Stanley Kubrick’s classic – now re-released – is safe. "))
                .andExpect(jsonPath("duration").value(139))
                .andExpect(jsonPath("rate").value(1));
    }


    @Test
    @SqlGroup({
            @Sql(value = USERSDATA, executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = FILMSDATA, executionPhase = BEFORE_TEST_METHOD)
    })
    @Transactional
    void shouldLikeFilm() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/films/{id}/like/{userId}", 1, 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @SqlGroup({
            @Sql(value = USERSDATA, executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = FILMSDATA, executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = LIKESDATA, executionPhase = BEFORE_TEST_METHOD)
    })
    @Transactional
    void shouldDeleteLikeFromFilm() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/films/{id}/like/{userId}", 2, 3))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @SqlGroup({
            @Sql(value = USERSDATA, executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = FILMSDATA, executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = LIKESDATA, executionPhase = BEFORE_TEST_METHOD)
    })
    @Transactional
    void shouldGetPopularityFilms() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/films/popular"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(3))
                .andExpect(jsonPath("$[2].id").value(2));

        //Check deleting of likes

        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/films/{id}/like/{userId}", 1, 1));
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/films/{id}/like/{userId}", 1, 2));
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/films/{id}/like/{userId}", 1, 3));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/films/popular"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(3))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[2].id").value(1));

        //Check adding of likes

        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/films/{id}/like/{userId}", 1, 1));
        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/films/{id}/like/{userId}", 1, 2));
        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/films/{id}/like/{userId}", 1, 3));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/films/popular"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(3))
                .andExpect(jsonPath("$[2].id").value(2));
    }


}
