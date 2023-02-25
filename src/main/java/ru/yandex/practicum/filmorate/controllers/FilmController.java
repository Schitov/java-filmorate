package ru.yandex.practicum.filmorate.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validators.ModelValidator;

import java.util.Collection;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    @Autowired
    private ModelValidator filmValidator;
    HashMap<Integer, Film> films = new HashMap<>();
    private int id = 0;

    @PostMapping()
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("add film: {}", film.toString());
        Errors errors = new BeanPropertyBindingResult(film, "film");
        filmValidator.validate(film, errors);
        film.setId(generatorId());
        films.put(film.getId(), film);
        return film;
    }

    @GetMapping()
    public Collection<Film> showFilms() {
        log.info("show films: {}", films.values().toString());
        return films.values();
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("update film: {}", film);
        Errors errors = new BeanPropertyBindingResult(film, "film");
        filmValidator.validate(film, errors);
        filmValidator.presentFilmValidate(film, films);
        films.replace(film.getId(), film);
        return film;
    }

    private int generatorId() {
        id = id + 1;
        return id;
    }

    public void clear() {
        films.clear();
    }
}
