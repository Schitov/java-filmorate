package ru.yandex.practicum.filmorate.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    @Autowired
    private FilmService filmService;

    @PostMapping()
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info(String.valueOf(film));
        return filmService.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmService.updateFilm(film);
//        return film;
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable("id") long filmId) {
        return filmService.getFilmById(filmId);
    }

    @GetMapping()
    public List<Film> showFilms() {
        return filmService.getFilms();
    }

    @PutMapping("/{id}/like/{userId}")
    public int likeFilm(@PathVariable("id") long filmId,
                        @PathVariable long userId) {
        return filmService.likeFilm(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public int deleteLikeFromFilm(@PathVariable("id") long filmId,
                                  @PathVariable long userId) {
        return filmService.removeLikeFilm(filmId, userId);
    }

    @GetMapping("/popular")
    public List<Film> getFilmPopularity(@RequestParam(defaultValue = "10") int count) {
        return filmService.showSortedFilms(count);
    }
}
