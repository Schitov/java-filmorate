package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import ru.yandex.practicum.filmorate.dao.UserStorage;
import ru.yandex.practicum.filmorate.dao.impl.FilmDBStorage;
import ru.yandex.practicum.filmorate.dao.impl.UserDBStorage;
import ru.yandex.practicum.filmorate.exceptions.ExistenceOfObjectException;
import ru.yandex.practicum.filmorate.exceptions.ValidException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validators.ModelValidator;

import java.util.List;

@Service
public class FilmService {

    UserStorage inMemoryUserStorage;
    FilmDBStorage filmDBStorage;

    @Autowired
    private ModelValidator filmValidator;

    @Autowired
    public FilmService(UserDBStorage userStorage, FilmDBStorage filmDBStorage) {
        this.inMemoryUserStorage = userStorage;
        this.filmDBStorage = filmDBStorage;
    }

    public List<Film> getFilms() {
        return filmDBStorage.getFilms();
    }

    public Film addFilm(Film film) {
        Errors errors = new BeanPropertyBindingResult(film, "film");
        filmValidator.validate(film, errors);
        return filmDBStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        Errors errors = new BeanPropertyBindingResult(film, "film");
        filmValidator.validate(film, errors);
        filmValidator.objectPresenceValidate(film.getId(), filmDBStorage.getIds());
        return filmDBStorage.updateFilm(film);
    }

    public Film getFilmById(long id) {
        if (checkPresenceAndPositiveOfValue(id)) {
            filmValidator.objectPresenceValidate(id, filmDBStorage.getIds());
            return filmDBStorage.getFilmById(id);
        }
        throw new ValidException("Id must be more than 0");
    }

    public int likeFilm(long idFilm, long idUser) {
        if (checkPresenceAndPositiveOfValues(idFilm, idUser)) {
            return filmDBStorage.likeFilm(idFilm, idUser);
        }
        throw new ValidException("idFilm or idUser must be more 0");
    }

    public int removeLikeFilm(long idFilm, long idUser) {
        if (checkPresenceAndPositiveOfValues(idFilm, idUser)) {
            return filmDBStorage.removeLikeFromFilm(idFilm, idUser);
        }
        throw new ExistenceOfObjectException("idFilm or idUser must be more 0");
    }

    public List<Film> showSortedFilms(int count) {
        if (filmDBStorage.getFilmsToCheckExistence().size() == 0) {
            throw new ValidException("No films are saved");
        }
        if (count > 0) {
            return filmDBStorage.showFilteredTopFilms(count);
        }
        throw new ValidException("Count must be more than 0");
    }

    public boolean checkPresenceAndPositiveOfValues(long idFilm, long idUser) {
        return idFilm > 0 & idUser > 0;
    }

    public boolean checkPresenceAndPositiveOfValue(long id) {
        return id >= 0;
    }


}
