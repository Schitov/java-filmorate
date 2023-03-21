package ru.yandex.practicum.filmorate.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.mappers.UserRowMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class UserDBStorage implements UserStorage {

    private long id = 0;

    private final JdbcTemplate jdbcTemplate;

    public UserDBStorage(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    @Override
    public User getUser(long id) {
        String sql = "select * from users where User_ID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new UserRowMapper());
    }

    @Override
    public int addFriend(long idUser, long idFriend) {
        String sql ="INSERT INTO FRIENDSHIP " +
                "(Friend_ID, " +
                " User_ID) " +
                "VALUES (?, ?)";

        return jdbcTemplate.update(
                sql,
                idFriend,
                idUser);
    }

    @Override
    public int deleteFriend(long idUser, long idFriend) {
        String sql = "DELETE FROM FRIENDSHIP WHERE User_ID = ? AND Friend_ID = ?";
        return jdbcTemplate.update(sql, idUser, idFriend);
    }

    @Override
    public User addUser(User user) {
        String sql ="INSERT INTO USERS (" +
                "EMAIL, " +
                "LOGIN, " +
                "NAME, " +
                "BIRTHDAY) " +
                "VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(
                sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday());

        String sqlOut = "select * from users order by User_ID desc limit 1";
        return jdbcTemplate.queryForObject(sqlOut, new UserRowMapper());
    }

    @Override
    public User updateUser(User user) {
        String sql = "UPDATE USERS " +
                "SET EMAIL = ?," +
                "LOGIN = ?," +
                "NAME = ?," +
                "BIRTHDAY = ?" +
                "WHERE User_ID = ?";

        jdbcTemplate.update(sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());
        return user;
    }

    @Override
    public Collection<User> showUsers() {
        String sql = "select * from users";

        List<User> users = jdbcTemplate.query(
                sql,
                new UserRowMapper());
        return users;
    }

    @Override
    public List<User> showFriends(long idUser) {
        log.info(String.valueOf(idUser));
        String sql = "SELECT *\n" +
                "FROM USERS\n" +
                "WHERE USERS.USER_ID in\n" +
                "(SELECT FRIENDSHIP.FRIEND_ID \n" +
                "FROM FRIENDSHIP \n" +
                "WHERE FRIENDSHIP.USER_ID = ?)";

        List<User> users = jdbcTemplate.query(sql, new Object[]{idUser}, new UserRowMapper());

        log.info("Common friends: " + users);

        return users;
    }

    @Override
    public List<User> showCommonFriends(long userId, long otherId) {
        String sql = "SELECT *\n" +
                    "FROM USERS\n" +
                    "WHERE USERS.USER_ID IN \n" +
                    "(SELECT FRIEND_ID\n" +
                    "FROM FRIENDSHIP \n" +
                    "WHERE FRIENDSHIP.USER_ID = ? AND FRIENDSHIP.FRIEND_ID  IN (SELECT  f.FRIEND_ID \n" +
                    "FROM FRIENDSHIP f \n" +
                    "WHERE f.USER_ID = ?))";

        return jdbcTemplate.query(sql, new Object[]{userId, otherId}, new UserRowMapper());

    }

    public List<Long> getIds() {
        String sql = "select User_ID from users";
        return jdbcTemplate.queryForList(sql, Long.class);
    }

    @Override
    public int generatorId() {
        return (int) ++id;
    }
}
