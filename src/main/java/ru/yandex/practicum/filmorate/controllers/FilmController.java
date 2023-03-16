package ru.yandex.practicum.filmorate.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;
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

//    @GetMapping()
//    public Collection<Film> showFilms() {
//        return filmService.getFilms().values();
//    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        filmService.updateFilm(film);
        return film;
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable("id") long filmId) {
        return filmService.getFilmById(filmId);
    }

//    @GetMapping("/sql/{id}")
//    public Film getFilmByIdSQL(@PathVariable("id") long filmId) {
//        return filmService.getFilmByIdSQL(filmId);
//    }

    @GetMapping()
    public List<Film> showFilms() {
        return filmService.getFilms();
    }

    @PutMapping("/{id}/like/{userId}")
    public Set<Long> likeFilm(@PathVariable("id") long filmId,
                              @PathVariable long userId) {
        return filmService.likeFilm(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Set<Long> deleteLikeFromFilm(@PathVariable("id") long filmId,
                                        @PathVariable long userId) {
        return filmService.removeLikeFilm(filmId, userId);
    }

    @GetMapping("/popular")
    public List<Film> getFilmPopularity(@RequestParam(defaultValue = "10") int count) {
        return filmService.showSortedFilms(count);
    }
}
