package ru.yandex.practicum.filmorate.mappers;

import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class FilmRowMapper implements RowMapper<Film> {

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        long filmId = rs.getLong("Film_id");
        String name = rs.getString("NAME");
        String description = rs.getString("DESCRIPTION");
        LocalDate releaseDate = rs.getDate("RELEASE_DATE").toLocalDate();
        long duration = rs.getLong("DURATION");
        String ratingMPA = rs.getString("RATING_MPA");
        long likes = rs.getLong("LIKES_AMOUNT");
        return new Film(filmId, name, description, releaseDate, duration);
    }

}
