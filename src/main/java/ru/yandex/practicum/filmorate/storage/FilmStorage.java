package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Component
public interface FilmStorage {

    HashMap<Integer, Film> getFilms();

    void addFilm(Film film);

    void updateFilm(Film film);

    int generatorId();

    public Film getFilmById(int id);

    public Set<Long> likeFilm(int idFilm, int idUser);

    public Set<Long> removeLikeFromFilm(int idFilm, int idUser);

    public List<Film> showFilteredTopFilms(int count);

}
