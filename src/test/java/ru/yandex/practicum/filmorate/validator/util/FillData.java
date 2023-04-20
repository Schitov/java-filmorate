package ru.yandex.practicum.filmorate.validator.util;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

public class FillData {
    public static Film getFilm() {

        return Film.builder()
                .id(1)
                .name("New film")
                .description("Incorporating both historical and fictionalized aspects,\n" +
                        "it is based on accounts of the sinking of the RMS Titanic and stars Kate Winslet and Leonardo DiCaprio")
                .releaseDate(LocalDate.of(1997, 3, 12))
                .duration(180)
                .rate(1)
                .mpa(new MPA(1))
                .genres(List.of(new Genre(1)))
                .build();
    }

    public static List<Film> getFilms() {
        Film film1 = Film.builder()
                .id(1)
                .name("Titanic")
                .description("Incorporating both historical and fictionalized aspects,\n" +
                        "it is based on accounts of the sinking of the RMS Titanic and stars Kate Winslet and Leonardo DiCaprio")
                .releaseDate(LocalDate.of(1997, 3, 12))
                .duration(160)
                .rate(1)
                .mpa(new MPA(1))
                .build();

        Film film2 = Film.builder()
                .id(2)
                .name("A Space Odyssey")
                .description("Both ‘Interstellar’\n" +
                        "and ‘Gravity’ took us out of this world, but the reputation of\n" +
                        "Stanley Kubrick’s classic – now re-released – is safe. ")
                .releaseDate(LocalDate.of(1968, 4, 2))
                .duration(139)
                .rate(1)
                .mpa(new MPA(1))
                .build();

        Film film3 = Film.builder()
                .id(3)
                .name("Seven Samurai")
                .description("Japanese epic samurai drama film co-written, edited, and directed by Akira Kurosawa.")
                .releaseDate(LocalDate.of(1954, 4, 26))
                .duration(207)
                .rate(1)
                .mpa(new MPA(1))
                .build();

        return List.of(film1, film2, film3);
    }


    public static User getUser() {

        return User.builder()
                .id(1)
                .email("newUser@mail.ru")
                .login("Newuser")
                .name("New user")
                .birthday(LocalDate.of(1991, 6, 12))
                .build();
    }

    public static List<User> getUsers() {
        User user1 = User.builder()
                .id(1)
                .email("grishin@mail.ru")
                .login("Grisha")
                .name("Lolo")
                .birthday(LocalDate.of(1991, 6, 12))
                .build();

        User user2 = User.builder()
                .id(2)
                .email("stolyarov@mail.ru")
                .login("Stolya")
                .name("Vladimir")
                .birthday(LocalDate.of(1988, 9, 12))
                .build();

        User user3 = User.builder()
                .id(3)
                .email("Lodkin@mail.ru")
                .login("Lodka")
                .name("Grisha")
                .birthday(LocalDate.of(1998, 6, 22))
                .build();

        return List.of(user1, user2, user3);
    }

}
