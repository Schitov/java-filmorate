package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public interface InMemoryFilmStorage {

    HashMap<Integer, Film> getFilms();

    void addFilm(Film film);

    void updateFilm(Film film);

    int generatorId();

    public Film getFilmById(Optional<Integer> id);

    public Set<Long> likeFilm(Optional<Integer> idFilm, Optional<Long> idUser);

    public Set<Long> removeLikeFromFilm(Optional<Integer> idFilm, Optional<Long> idUser);

    public List<Film> showFilteredTopFilms(int count);
    public void clear();

}
