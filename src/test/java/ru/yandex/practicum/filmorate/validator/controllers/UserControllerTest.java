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
import ru.yandex.practicum.filmorate.model.User;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.yandex.practicum.filmorate.validator.util.Constants.FRIENDSDATA;
import static ru.yandex.practicum.filmorate.validator.util.Constants.USERSDATA;
import static ru.yandex.practicum.filmorate.validator.util.FillData.getUser;
import static ru.yandex.practicum.filmorate.validator.util.ObjectMapper.objMapper;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql(value = USERSDATA, executionPhase = BEFORE_TEST_METHOD)
    @Transactional
    void shouldGetUsers() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name").value("Lolo"))
                .andExpect(jsonPath("$[0].email").value("grishin@mail.ru"))
                .andExpect(jsonPath("$[0].login").value("Grisha"))
                .andExpect(jsonPath("$[0].birthday").value("1991-06-12"))
                .andExpect(jsonPath("$[2].name").value("Grisha"))
                .andExpect(jsonPath("$[2].email").value("Lodkin@mail.ru"))
                .andExpect(jsonPath("$[2].login").value("Lodka"))
                .andExpect(jsonPath("$[2].birthday").value("1998-06-22"));

    }

    @Test
    @Transactional
    void shouldCreateUser() throws Exception {
        User user = getUser();
        String jsonUser = objMapper(user);
        this.mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUser))
                .andDo(print())
                .andExpect(jsonPath("name").value("New user"))
                .andExpect(jsonPath("email").value("newUser@mail.ru"))
                .andExpect(jsonPath("login").value("Newuser"))
                .andExpect(jsonPath("birthday").value("1991-06-12"));
    }

    @Test
    @Sql(value = USERSDATA, executionPhase = BEFORE_TEST_METHOD)
    @Transactional
    void shouldUpdateUser() throws Exception {
        User user = getUser();
        String jsonUser = objMapper(user);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUser))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name").value("New user"));
    }

    @Test
    @Sql(value = USERSDATA, executionPhase = BEFORE_TEST_METHOD)
    @Transactional
    void shouldGetUserById() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name").value("Lolo"));

    }

    @Test
    @SqlGroup({
            @Sql(value = USERSDATA, executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = FRIENDSDATA, executionPhase = BEFORE_TEST_METHOD)
    })
    @Transactional
    void shouldGetFriends() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}/friends", 3))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Lolo"))
                .andExpect(jsonPath("$[1].name").value("Vladimir"));
    }

    @Test
    @SqlGroup({
            @Sql(value = USERSDATA, executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = FRIENDSDATA, executionPhase = BEFORE_TEST_METHOD)
    })
    @Transactional
    void shouldShowCommonFriends() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}/friends/common/{otherId}", 3, 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Vladimir"));
    }

    @Test
    @SqlGroup({
            @Sql(value = USERSDATA, executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = FRIENDSDATA, executionPhase = BEFORE_TEST_METHOD)
    })
    @Transactional
    void shouldDeleteFriend() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/{id}/friends/{friendId}", 3, 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}/friends", 3))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Vladimir"));
    }


    @Test
    @SqlGroup({
            @Sql(value = USERSDATA, executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = FRIENDSDATA, executionPhase = BEFORE_TEST_METHOD)
    })
    @Transactional
    void shouldAddFriend() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/{id}/friends/{friendId}", 1, 3))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}/friends", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Vladimir"))
                .andExpect(jsonPath("$[1].name").value("Grisha"));
    }


}