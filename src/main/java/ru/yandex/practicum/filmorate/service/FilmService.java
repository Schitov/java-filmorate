package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import ru.yandex.practicum.filmorate.exceptions.ExistenceOfObjectException;
import ru.yandex.practicum.filmorate.exceptions.ValidException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.ImMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.validators.ModelValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class FilmService {

    FilmStorage filmStorage;
    UserStorage inMemoryUserStorage;

    @Autowired
    private ModelValidator filmValidator;

    @Autowired
    public FilmService(InMemoryFilmStorage inMemoryFilmStorage1, ImMemoryUserStorage userStorage) {
        this.filmStorage = inMemoryFilmStorage1;
        this.inMemoryUserStorage = userStorage;
    }

    public HashMap<Integer, Film> getFilms() {
        return filmStorage.getFilms();
    }

    public void addFilm(Film film) {
        Errors errors = new BeanPropertyBindingResult(film, "film");
        filmValidator.validate(film, errors);
        filmStorage.addFilm(film);
    }

    public void updateFilm(Film film) {
        Errors errors = new BeanPropertyBindingResult(film, "film");
        filmValidator.validate(film, errors);
        filmValidator.objectPresenceValidate(film.getId(), filmStorage.getFilms());
        filmStorage.updateFilm(film);
    }

    public Film getFilmById(int id) {
        if (checkPresenceAndPositiveOfValue(id)) {
            filmValidator.objectPresenceValidate(id, filmStorage.getFilms());
            return filmStorage.getFilmById(id);
        }
        throw new ValidException("Id must be more than 0");
    }

    public Set<Long> likeFilm(int idFilm, int idUser) {
        if (checkPresenceAndPositiveOfValues(idFilm, idUser)) {
            filmValidator.objectPresenceValidate(idFilm, filmStorage.getFilms());
            filmValidator.objectPresenceValidate(idUser, inMemoryUserStorage.getUsers());
            return filmStorage.likeFilm(idFilm, idUser);
        }
        throw new ValidException("idFilm or idUser must be more 0");
    }

    public Set<Long> removeLikeFilm(int idFilm, int idUser) {
        if (checkPresenceAndPositiveOfValues(idFilm, idUser)) {
            filmValidator.objectPresenceValidate(idFilm, filmStorage.getFilms());
            filmValidator.objectPresenceValidate(idUser, inMemoryUserStorage.getUsers());
            return filmStorage.removeLikeFromFilm(idFilm, idUser);
        }
        throw new ExistenceOfObjectException("idFilm or idUser must be more 0");
    }

    public List<Film> showSortedFilms(int count) {
        if (filmStorage.getFilms().size() == 0) {
            throw new ValidException("No films are saved");
        }
        if (count > 0) {
            return filmStorage.showFilteredTopFilms(count);
        }
        throw new ValidException("Count must be more than 0");
    }

    public boolean checkPresenceAndPositiveOfValues(int idFilm, int idUser) {
        if (idFilm > 0 & idUser > 0) {
            return true;
        }
        return false;
    }

    public boolean checkPresenceAndPositiveOfValue(int id) {
        if (id >= 0) {
            return true;
        }
        return false;
    }


}
