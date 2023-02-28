package ru.yandex.practicum.filmorate.validator;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.exceptions.ExistenceOfObject;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
class ModelValidatorTest {

    @Autowired
    FilmController filmController;
    @Autowired
    UserController userController;

    @Test
    void checkBirthDateOfFilm() {
        Film film = Film.builder()
                .name("testFilm")
                .description("test description")
                .duration(120)
                .releaseDate(LocalDate.of(1890, 12, 12))
                .build();
        Throwable throwable = assertThrows(ValidException.class, () -> filmController.addFilm(film));
        assertEquals("The film cannot be made before 28.12.1895", throwable.getMessage());
    }

    @Test
    void checkEmptyNameOfFilm() {
        Film film1 = Film.builder()
                .name("Film")
                .description("test description")
                .duration(120)
                .releaseDate(LocalDate.of(1990, 12, 12))
                .build();

        Film film2 = Film.builder()
                .id(3)
                .name(" ")
                .description("test description")
                .duration(120)
                .releaseDate(LocalDate.of(1990, 12, 12))
                .build();

        filmController.addFilm(film1);

        Throwable throwable = assertThrows(ValidException.class, () -> filmController.updateFilm(film2));
        assertEquals(1, filmController.showFilms().size());
        assertEquals("Name of film is empty", throwable.getMessage());
    }


    @Test
    void checkLengthLessThan200() {
        Film film1 = Film.builder()
                .name("Film")
                .description("test description test description test description test description test description" +
                        "test description test description test description test description test description " +
                        "test description test description test descriptiontest description")
                .duration(120)
                .releaseDate(LocalDate.of(1990, 12, 12))
                .build();

        Throwable throwable = assertThrows(ValidException.class, () -> filmController.addFilm(film1));
        assertEquals(0, filmController.showFilms().size());
        assertEquals("Length of description more than 200 chars, please change it", throwable.getMessage());
    }

    @Test
    void checkDurationNotNegative() {
        Film film1 = Film.builder()
                .name("Film")
                .description("test description")
                .duration(-10)
                .releaseDate(LocalDate.of(1990, 12, 12))
                .build();

        Throwable throwable = assertThrows(ValidException.class, () -> filmController.addFilm(film1));
        assertEquals(0, filmController.showFilms().size());
        assertEquals("Duration must be more 0", throwable.getMessage());
    }

    @Test
    void checkUpdateNotExistedUser() {
        User user1 = User.builder()
                .birthday(LocalDate.of(1993, 12, 21))
                .email("wer@mail.ru")
                .login("test")
                .build();

        User user2 = User.builder()
                .id(2)
                .birthday(LocalDate.of(1993, 12, 21))
                .email("wer@mail.ru")
                .login("test")
                .build();

        userController.addUser(user1);

        Throwable throwable = assertThrows(ExistenceOfObject.class, () -> userController.updateUser(user2));
        assertEquals("User with number2 is not existed", throwable.getMessage());
    }

    @Test
    void checkEmptyLoginOfUser() {
        User user1 = User.builder()
                .birthday(LocalDate.of(1993, 12, 21))
                .email("wer@mail.ru")
                .login(" ")
                .build();

        Throwable throwable = assertThrows(ValidException.class, () -> userController.addUser(user1));
        assertEquals("Login musn't contain spaces", throwable.getMessage());
    }

    @Test
    void checkBirthdayOfUser() {
        User user1 = User.builder()
                .birthday(LocalDate.of(2025, 12, 21))
                .email("wer@mail.ru")
                .login("Iam")
                .build();

        Throwable throwable = assertThrows(ValidException.class, () -> userController.addUser(user1));
        assertEquals("This birthday is not existed", throwable.getMessage());
    }
}
