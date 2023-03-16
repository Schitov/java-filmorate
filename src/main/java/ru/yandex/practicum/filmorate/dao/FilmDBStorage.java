package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.mappers.FilmRowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FilmDBStorage implements FilmStorage {
    private long id = 0;
    HashMap<Long, Film> films = new HashMap<>();

    private final JdbcTemplate jdbcTemplate;

    public FilmDBStorage(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    @Override
    public List<Film> getFilms() {
        String sql = "select * from film";

        List<Film> films = jdbcTemplate.query(
                sql,
                new FilmRowMapper());
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
    public Film getFilmById(long id) {
        String sql = "select * from film where Film_ID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new FilmRowMapper());
    }

//    @Override
//    public List<Film> getFilmsByIdSQL() {
////        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("select * from film");
//        String sql = "select * from film";
//
//        List<Film> films = jdbcTemplate.query(
//                sql,
//                new FilmRowMapper());
//        return films;
//    }

    @Override
    public Set<Long> likeFilm(long idFilm, long idUser) {
        films.get(idFilm).addLike(idUser);
        return films.get(idFilm).getLikes();
    }

    @Override
    public Set<Long> removeLikeFromFilm(long idFilm, long idUser) {
        films.get(idFilm).removeLike(idUser);
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

    private Film makePost(ResultSet rs) throws SQLException {
        // используем конструктор, методы ResultSet
        // и готовое значение user
        long filmId = rs.getLong("Film_id");
        String name = rs.getString("NAME");
        String description = rs.getString("DESCRIPTION");
        LocalDate releaseDate = rs.getDate("RELEASE_DATE").toLocalDate();
        long duration = rs.getLong("DURATION");
        String ratingMPA = rs.getString("RATING_MPA");
        long likes = rs.getLong("LIKES_AMOUNT");
        return new Film(filmId, name, description, releaseDate, duration);
    }

    @Override
    public int generatorId() {
        return (int) ++id;
    }
}
