package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreStorage {
//    public List<Genre> getGenres();
//    public Genre getGenreById();

    public List<Genre> getGenres();
    public Genre getGenreById(int id);
    public List<Genre> getGenresOfFilm(int id);
    public List<Long> getIds();


}
