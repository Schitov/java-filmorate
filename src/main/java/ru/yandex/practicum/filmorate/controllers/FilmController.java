package ru.yandex.practicum.filmorate.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/films")
public class FilmController {
    @Autowired
    private FilmService filmService;

    @PostMapping()
    public Film addFilm(@Valid @RequestBody Film film) {
        filmService.addFilm(film);
        return film;
    }

    @GetMapping()
    public Collection<Film> showFilms() {
        return filmService.getFilms().values();
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        filmService.updateFilm(film);
        return film;
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable("id") Optional<Integer> filmId) {
        return filmService.getFilmById(filmId);
    }

    @PutMapping("/{id}/like/{userId}")
    public Set<Long> likeFilm(@PathVariable("id") Optional<Integer> filmId,
                              @PathVariable Optional<Long> userId) {
        return filmService.likeFilm(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Set<Long> deleteLikeFromFilm(@PathVariable("id") Optional<Integer> filmId,
                                        @PathVariable Optional<Long> userId) {
        return filmService.removeLikeFilm(filmId, userId);
    }

    @GetMapping("/popular")
    public List<Film> getFilmPopularity(@RequestParam(defaultValue = "10") int count) {
        return filmService.showSortedFilms(count);
    }

    public void clear() {
        filmService.clear();
    }
}
