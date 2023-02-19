package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FilmorateApplicationTests {

    FilmController filmController = new FilmController();
    UserController userController = new UserController();

    @Test
    void checkBirthDateOfFilm() {
        Film film = Film.builder()
                .name("testFilm")
                .description("test description")
                .duration(120)
                .releaseDate(LocalDate.of(1890, 12, 12))
                .build();
        Throwable throwable = assertThrows(ValidException.class, () -> filmController.addFilm(film));
        assertEquals("Release date less than birthday of cinema", throwable.getMessage());
    }

    @Test
    void updateFilmInCollection() {
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
        assertEquals("Film is not existed in our library", throwable.getMessage());
    }

    @Test
    void checkUpdateUser() {
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

        Throwable throwable = assertThrows(ValidException.class, () -> userController.updateUser(user2));
        assertEquals("User with number is not existed", throwable.getMessage());

    }
}
