package ru.yandex.practicum.filmorate.mappers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
public class FilmRowMapper implements RowMapper<Film> {

    List<Genre> genres;

    public FilmRowMapper() {};

    public FilmRowMapper(List<Genre> genres) {
        this.genres = genres;
    };


    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        long filmId = rs.getLong("FILM.FILM_id");
        String name = rs.getString("FILM.NAME");
        String description = rs.getString("FILM.DESCRIPTION");
        LocalDate releaseDate = rs.getDate("RELEASE_DATE").toLocalDate();
        long duration = rs.getLong("DURATION");
        MPA ratingMPA = new MPA(rs.getInt("RATING_MPA"), rs.getString("Rating_MPA.NAME"));
        int rate = rs.getInt("RATE");
        long likes = rs.getLong("LIKES_AMOUNT");
        Film film = new Film(filmId, name, description, releaseDate, duration, ratingMPA, rate);
        if(genres != null) {
            film.setGenres(genres);
        }

//        List<Genre> genres = rs.getArray();
        log.info(rs.getString("Rating_MPA"));

        return film;
    }
}
