package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import ru.yandex.practicum.filmorate.exceptions.ExistenceOfObject;
import ru.yandex.practicum.filmorate.exceptions.ValidException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.validators.ModelValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class FilmService {

    InMemoryFilmStorage inMemoryFilmStorage;
    InMemoryUserStorage inMemoryUserStorage;

    @Autowired
    private ModelValidator filmValidator;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.inMemoryFilmStorage = filmStorage;
        this.inMemoryUserStorage = userStorage;
    }

    public void clear() {
        inMemoryFilmStorage.clear();
    }

    public HashMap<Integer, Film> getFilms() {
        return inMemoryFilmStorage.getFilms();
    }

    public void addFilm(Film film) {
        Errors errors = new BeanPropertyBindingResult(film, "film");
        filmValidator.validate(film, errors);
        inMemoryFilmStorage.addFilm(film);
    }

    public void updateFilm(Film film) {
        Errors errors = new BeanPropertyBindingResult(film, "film");
        filmValidator.validate(film, errors);
        filmValidator.presentFilmValidate(film, inMemoryFilmStorage.getFilms());
        inMemoryFilmStorage.updateFilm(film);
    }

    public Film getFilmById(Optional<Integer> id) {
        log.info(String.valueOf(id));
        log.info(String.valueOf(checkPresenceAndPositiveOfValue(id)));
        if (checkPresenceAndPositiveOfValue(id)) {
            filmValidator.presentFilmValidateById(id.get().intValue(), inMemoryFilmStorage.getFilms());
            return inMemoryFilmStorage.getFilmById(id);
        }
        throw new ValidException("Id must be more than 0");
    }

    public Set<Long> likeFilm(Optional<Integer> idFilm, Optional<Long> idUser) {
        if (checkPresenceAndPositiveOfValues(idFilm, idUser)) {
            filmValidator.presentFilmValidateById(idFilm.get().intValue(), inMemoryFilmStorage.getFilms());
            filmValidator.presentUserValidateById(idUser.get().intValue(), inMemoryUserStorage.getUsers());
            return inMemoryFilmStorage.likeFilm(idFilm, idUser);
        }
        throw new ValidException("idFilm or idUser must be more 0");
    }

    public Set<Long> removeLikeFilm(Optional<Integer> idFilm, Optional<Long> idUser) {
        if (checkPresenceAndPositiveOfValues(idFilm, idUser)) {
            filmValidator.presentFilmValidateById(idFilm.get().intValue(), inMemoryFilmStorage.getFilms());
            filmValidator.presentUserValidateById(idUser.get().intValue(), inMemoryUserStorage.getUsers());
            return inMemoryFilmStorage.removeLikeFromFilm(idFilm, idUser);
        }
        throw new ExistenceOfObject("idFilm or idUser must be more 0");
    }

    public List<Film> showSortedFilms(int count) {
        if (inMemoryFilmStorage.getFilms().size() == 0) {
            throw new ValidException("No films are saved");
        }
        if (count > 0) {
            return inMemoryFilmStorage.showFilteredTopFilms(count);
        }
        throw new ValidException("Count must be more than 0");
    }

    public boolean checkPresenceAndPositiveOfValues(Optional<Integer> idFilm, Optional<Long> idUser) {
        if (idFilm.isPresent() & idUser.isPresent() & idFilm.get() > 0 & idUser.get() > 0) {
            return true;
        }
        return false;
    }

    public boolean checkPresenceAndPositiveOfValue(Optional<Integer> id) {
        if (id.isPresent() & id.get().intValue() >= 0) {
            return true;
        }
        return false;
    }


}
