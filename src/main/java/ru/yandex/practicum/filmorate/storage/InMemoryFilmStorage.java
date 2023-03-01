package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {
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
    public Film getFilmById(int id) {
        return films.get(id);
    }

    @Override
    public Set<Long> likeFilm(int idFilm, int idUser) {
        films.get(idFilm).addLike(idUser);
        return films.get(idFilm).getLikes();
    }

    @Override
    public Set<Long> removeLikeFromFilm(int idFilm, int idUser) {
        films.get(idFilm).removeLike(Long.valueOf(idUser));
        return films.get(idFilm).getLikes();
    }

    @Override
    public List<Film> showFilteredTopFilms(int count) {
        return films.values()
                .stream()
                .sorted((o1, o2) -> o2.countOfLikes() - o1.countOfLikes())
                .limit(count)
                .collect(Collectors.toList());

    }

    @Override
    public int generatorId() {
        return ++id;
    }
}
