package ru.yandex.practicum.filmorate.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    HashMap<Integer, Film> films = new HashMap<>();
    private int id = 0;
    private final static LocalDate BIRTHDAY_OF_FILMS = LocalDate.of(1895, 12, 28);

    @PostMapping()
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("add film {}", film.toString());
        checkReleaseDate(film);
        film.setId(generatorId());
        films.put(film.getId(), film);
        return film;
    }

    @GetMapping()
    public Collection<Film> showFilms() {
        log.info("show films {}", films.values().toString());
        return films.values();
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("update film {}", film);
        checkReleaseDate(film);
        checkFilmInFilms(film);
        films.replace(film.getId(), film);
        return film;
    }

    private void checkReleaseDate(Film film) {
        if (film.getReleaseDate().isBefore(BIRTHDAY_OF_FILMS)) {
            throw new ValidException("Release date less than birthday of cinema");
        }
    }

    private void checkFilmInFilms(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new ValidException("Film is not existed in our library");
        }
    }

    private int generatorId() {
        id = id + 1;
        return id;
    }

}
