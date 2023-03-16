package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import ru.yandex.practicum.filmorate.dao.FilmDBStorage;
import ru.yandex.practicum.filmorate.dao.UserDBStorage;
import ru.yandex.practicum.filmorate.exceptions.ExistenceOfObjectException;
import ru.yandex.practicum.filmorate.exceptions.ValidException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.validators.ModelValidator;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class FilmService {

    FilmStorage filmStorage;
    UserStorage inMemoryUserStorage;
    FilmDBStorage filmDBStorage;

    @Autowired
    private ModelValidator filmValidator;

    @Autowired
    public FilmService(FilmDBStorage inMemoryFilmStorage1, UserDBStorage userStorage, FilmDBStorage filmDBStorage) {
        this.filmStorage = inMemoryFilmStorage1;
        this.inMemoryUserStorage = userStorage;
        this.filmDBStorage = filmDBStorage;
    }

    public List<Film> getFilms() {
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
//        filmValidator.objectPresenceValidate(film.getId(), filmStorage.getFilms());
        filmStorage.updateFilm(film);
    }

    public Film getFilmById(long id) {
        if (checkPresenceAndPositiveOfValue(id)) {
//            filmValidator.objectPresenceValidate(id, filmStorage.getFilms());
            return filmStorage.getFilmById(id);
        }
        throw new ValidException("Id must be more than 0");
    }

//    public Film getFilmByIdSQL(long id) {
//        return filmDBStorage.getFilmByIdSQL(id);
//    }

//    public List<Film> getFilmsByIdSQL() {
//        return filmDBStorage.getFilmsByIdSQL();
//    }

    public Set<Long> likeFilm(long idFilm, long idUser) {
        if (checkPresenceAndPositiveOfValues(idFilm, idUser)) {
//            filmValidator.objectPresenceValidate(idFilm, filmStorage.getFilms());
//            filmValidator.objectPresenceValidate(idUser, inMemoryUserStorage.getUsers());
            return filmStorage.likeFilm(idFilm, idUser);
        }
        throw new ValidException("idFilm or idUser must be more 0");
    }

    public Set<Long> removeLikeFilm(long idFilm, long idUser) {
        if (checkPresenceAndPositiveOfValues(idFilm, idUser)) {
//            filmValidator.objectPresenceValidate(idFilm, filmStorage.getFilms());
//            filmValidator.objectPresenceValidate(idUser, inMemoryUserStorage.getUsers());
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

    public boolean checkPresenceAndPositiveOfValues(long idFilm, long idUser) {
        if (idFilm > 0 & idUser > 0) {
            return true;
        }
        return false;
    }

    public boolean checkPresenceAndPositiveOfValue(long id) {
        if (id >= 0) {
            return true;
        }
        return false;
    }


}
