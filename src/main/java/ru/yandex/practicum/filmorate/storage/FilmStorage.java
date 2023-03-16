package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Set;

@Component
public interface FilmStorage {

    List<Film> getFilms();

    void addFilm(Film film);

    void updateFilm(Film film);

    int generatorId();

    public Film getFilmById(long id);

    public Set<Long> likeFilm(long idFilm, long idUser);

    public Set<Long> removeLikeFromFilm(long idFilm, long idUser);

    public List<Film> showFilteredTopFilms(int count);
//    public Film getFilmByIdSQL(long id);
//    public List<Film> getFilmsByIdSQL();

}
