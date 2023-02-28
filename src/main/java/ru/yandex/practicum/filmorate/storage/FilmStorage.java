package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FilmStorage implements InMemoryFilmStorage {
    private int id = 0;
    HashMap<Integer, Film> films = new HashMap<>();

    @Override
    public HashMap<Integer, Film> getFilms() {
        return films;
    }

    @Override
    public void addFilm(Film film) {
        film.setId(generatorId());
        films.put(film.getId(), film);
    }

    @Override
    public void updateFilm(Film film) {
        films.replace(film.getId(), film);
    }

    @Override
    public Film getFilmById(Optional<Integer> id) {
        return films.get(id.get());
    }

    @Override
    public Set<Long> likeFilm(Optional<Integer> idFilm, Optional<Long> idUser) {
        films.get(idFilm.get()).addLike(idUser.get());
        return films.get(idFilm.get()).getLikes();
    }

    @Override
    public Set<Long> removeLikeFromFilm(Optional<Integer> idFilm, Optional<Long> idUser) {
        films.get(idFilm.get()).removeLike(idUser.get());
        return films.get(idFilm.get()).getLikes();
    }

    @Override
    public List<Film> showFilteredTopFilms(int count) {
        if (films.values() == null) {
            return null;
        }
        return films.values()
                .stream()
                .sorted((o1, o2) -> o2.countOfLikes() - o1.countOfLikes())
                .limit(count)
                .collect(Collectors.toList());

    }

    @Override
    public int generatorId() {
        id = id + 1;
        return id;
    }
}
