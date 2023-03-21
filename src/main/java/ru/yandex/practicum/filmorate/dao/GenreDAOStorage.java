package ru.yandex.practicum.filmorate.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.mappers.GenreRowMapper;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.List;

@Slf4j
@Component
public class GenreDAOStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    public GenreDAOStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Genre> getGenres() {
        String sql = "select * from genre";
        return jdbcTemplate.query(sql, new GenreRowMapper());
    }

    ;

    public Genre getGenreById(int id) {
        String sql = "select * from genre where genreID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new GenreRowMapper());
    }

    public List<Genre> getGenresOfFilm(int id) {
        String sql = "SELECT *\n" +
                "FROM GENRE\n" +
                "WHERE GENRE.GENREID IN \n" +
                "(SELECT GENRE_FILM.GENRE_ID\n" +
                "FROM GENRE_FILM\n" +
                "WHERE GENRE_FILM.FILM_ID = ?)";

        log.info(String.valueOf(id));

        List<Genre> genres = jdbcTemplate.query(sql, new Object[]{id}, new GenreRowMapper());

        log.info("Genres: " + genres);

        return genres;
    }

    @Override
    public List<Long> getIds() {
        String sql = "Select genreID from GENRE";
        return jdbcTemplate.queryForList(sql, Long.class);
    }

}
