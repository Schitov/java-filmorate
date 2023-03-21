package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Component
public interface FilmStorage {

    List<Film> getFilms();

    Film addFilm(Film film);

    Film updateFilm(Film film);

    public Film getFilmById(long id);

    public int likeFilm(long idFilm, long idUser);

    public int removeLikeFromFilm(long idFilm, long idUser);

    public List<Film> showFilteredTopFilms(int count);

    public void addGenreToFilm(long idFilm, long idGenre);

    public Film getFilmByIdWithGenres(long id, List<Genre> genres);

    public void updateGenreOfFilm(long idFilm, long idGenre);

}
