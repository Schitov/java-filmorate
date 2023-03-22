package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.mappers.FilmRowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.dao.FilmStorage;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class FilmDBStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final GenreDAOStorage genreStorage;

    public FilmDBStorage(JdbcTemplate jdbcTemplate, GenreDAOStorage genreDAOStorage) {
        this.genreStorage = genreDAOStorage;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Film> getFilms() {
        String sql = "SELECT *\n" +
                "FROM film\n" +
                "INNER JOIN RATING_MPA ON film.RATING_MPA = RATING_MPA.RATING_ID";

        List<Film> films = jdbcTemplate.query(
                sql,
                new FilmRowMapper());

        for (Film film : films) {
            if (film.getGenres() != null) {
                film.setGenres(genreStorage.getGenresOfFilm((int) film.getId()));
            }
        }

        return films;
    }

    @Override
    public List<Film> getFilmsToCheckExistence() {
        String sql = "SELECT *\n" +
                "FROM film\n" +
                "INNER JOIN RATING_MPA ON film.RATING_MPA = RATING_MPA.RATING_ID " +
                "LIMIT 1";

        List<Film> films = jdbcTemplate.query(
                sql,
                new FilmRowMapper());

        for (Film film : films) {
            if (film.getGenres() != null) {
                film.setGenres(genreStorage.getGenresOfFilm((int) film.getId()));
            }
        }

        return films;
    }

    @Override
    public Film getFilmById(long id) {
        List<Genre> genres = genreStorage.getGenresOfFilm((int) id);

        String sql = "SELECT *\n" +
                "FROM film\n" +
                "INNER JOIN RATING_MPA ON film.RATING_MPA = RATING_MPA.RATING_ID " +
                "WHERE FILM_ID = ?";

        return jdbcTemplate.queryForObject(sql, new FilmRowMapper(genres), id);
    }

    @Override
    public Film getFilmByIdWithGenres(long id, List<Genre> genres) {

        log.info("Genres: {}", genres);

        String sqlFilm = "SELECT *\n" +
                "FROM FILM\n" +
                "LEFT JOIN RATING_MPA ON film.RATING_MPA = RATING_MPA.RATING_ID \n" +
                "WHERE FILM.FILM_ID = ?";

        return jdbcTemplate.queryForObject(sqlFilm, (rs, rowNum) -> Film.builder()
                        .id(rs.getInt("Film_ID"))
                        .name(rs.getString("FILM.NAME"))
                        .description(rs.getString("FILM.DESCRIPTION"))
                        .releaseDate(rs.getDate("RELEASE_DATE").toLocalDate())
                        .duration(rs.getLong("DURATION"))
                        .rate(rs.getInt("RATE"))
                        .mpa(new MPA(rs.getInt("RATING_MPA"), rs.getString("Rating_MPA.NAME")))
                        .genres(genres)
                        .build()
                , id);
    }

    @Override
    public Film addFilm(Film film) {

        String sql = "INSERT INTO FILM (" +
                "name, " +
                "description, " +
                "RELEASE_DATE, " +
                "duration, " +
                "rate, " +
                "Rating_MPA) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(
                sql,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRate(),
                film.getMpa().getId());

        Film lastFilm = showLastFilm();

        if (!film.getGenres().isEmpty()) {

            log.info("Genres from film: {}", film.getGenres());
            updateGenresDuringAdding(film.getGenres(), lastFilm.getId());
            List<Genre> genres = genreStorage.getGenresOfFilm((int) lastFilm.getId());

            return getFilmByIdWithGenres(lastFilm.getId(), genres);
        }

        return lastFilm;
    }

    @Override
    public void addGenreToFilm(long idFilm, long idGenre) {
        log.info("ID of Film: " + idFilm);
        log.info("ID of Genre: " + idGenre);
        String sql = "INSERT INTO GENRE_FILM " +
                "(FILM_ID, " +
                "GENRE_ID) " +
                "VALUES (?, ?)";

        jdbcTemplate.update(
                sql,
                idFilm,
                idGenre);
    }

    @Override
    public void updateGenreOfFilm(long idFilm, long idGenre) {
        log.info("ID of Film: {}", idFilm);
        log.info("ID of Genre: {}", idGenre);

        String sql = "INSERT INTO GENRE_FILM " +
                "(FILM_ID, " +
                "GENRE_ID) " +
                "VALUES (?, ?)";

        jdbcTemplate.update(
                sql,
                idFilm,
                idGenre);
    }

    @Override
    public Film updateFilm(Film film) {

        String sql = "UPDATE FILM " +
                "SET name = ?," +
                "description = ?," +
                "RELEASE_DATE = ?," +
                "duration = ?," +
                "rate = ?," +
                "Rating_MPA = ?," +
                "LIKES_AMOUNT = ?" +
                "WHERE FILM_ID = ?";

        jdbcTemplate.update(sql,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRate(),
                film.getMpa().getId(),
                film.getLikes(),
                film.getId());
        if (film.getGenres() != null) {
            updateGenresDuringAdding(film.getGenres(), film.getId());
        }

        return getFilmById(film.getId());
    }

    @Override
    public int likeFilm(long idFilm, long idUser) {
        String sqlLikes = "INSERT INTO LIKES (Film_id, USER_ID) VALUES (?,?)";

        return jdbcTemplate.update(sqlLikes, idFilm, idUser);

    }

    @Override
    public int removeLikeFromFilm(long idFilm, long idUser) {
        String sql = "DELETE FROM LIKES WHERE Film_id = ? AND USER_ID = ?";
        return jdbcTemplate.update(sql, idFilm, idUser);
    }

    @Override
    public List<Film> showFilteredTopFilms(int count) {
        String sql = "SELECT *\n" +
                "FROM FILM \n" +
                "LEFT JOIN (SELECT LIKES.FILM_ID, COUNT(*) AS likes_count\n" +
                "FROM LIKES\n" +
                "GROUP BY FILM_ID\n" +
                "ORDER BY likes_count DESC) t ON film.FILM_ID = t.FILM_ID\n" +
                "LEFT JOIN RATING_MPA ON film.RATING_MPA = RATING_MPA.RATING_ID " +
                "ORDER BY likes_count DESC \n" +
                "LIMIT ?";

        return jdbcTemplate.query(
                sql,
                new FilmRowMapper(),
                count);
    }

    public List<Long> getIds() {
        String sql = "select Film_ID from film";
        return jdbcTemplate.queryForList(sql, Long.class);
    }

    public Film showLastFilm() {
        String sqlOut = "SELECT *\n" +
                "FROM film\n" +
                "INNER JOIN RATING_MPA ON film.RATING_MPA = RATING_MPA.RATING_ID " +
                "ORDER BY FILM_ID DESC " +
                "LIMIT 1";

        Film film = jdbcTemplate.queryForObject(sqlOut, new FilmRowMapper());

        log.info("film: {}", film);

        return film;
    }

    public void updateGenresDuringAdding(List<Genre> genres, long id) {

        if (genres.isEmpty() & !genreStorage.getGenresOfFilm((int) id).isEmpty()) {
            removeGenres(id);
        } else {
            removeGenres(id);
            List<Integer> ids = new ArrayList<>();
            for (Genre genre : genres) {
                if (!ids.contains(genre.getId())) {
                    updateGenreOfFilm(id, genre.getId());
                    ids.add(genre.getId());
                }
            }
        }
    }

    public void removeGenres(long id) {
        String sql = "DELETE FROM " +
                "GENRE_FILM " +
                "WHERE FILM_ID = ?";

        jdbcTemplate.update(
                sql,
                id);
    }
}
