package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.mappers.MpaRowMapper;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.dao.MPAStorage;

import java.util.List;

@Repository
public class MpaDAOStorage implements MPAStorage {

    private final JdbcTemplate jdbcTemplate;

    public MpaDAOStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MPA> getMpas() {
        String sql = "select * from Rating_MPA";
        return jdbcTemplate.query(sql, new MpaRowMapper());
    }

    @Override
    public MPA getMpaById(int id) {
        String sql = "select * from Rating_MPA where Rating_ID = ?";
        return jdbcTemplate.queryForObject(sql, new MpaRowMapper(), id);
    }

    @Override
    public List<Long> getIds() {
        String sql = "Select Rating_ID from Rating_MPA";
        return jdbcTemplate.queryForList(sql, Long.class);
    }
}
