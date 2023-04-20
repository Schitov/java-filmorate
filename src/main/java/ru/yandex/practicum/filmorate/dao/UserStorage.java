package ru.yandex.practicum.filmorate.dao;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
public interface UserStorage {
    User addUser(User user);

    Collection<User> showUsers();

    User updateUser(User user);

    public User getUser(long id);

    public int addFriend(long idUser, long idFriend);

    public int deleteFriend(long idUser, long idFriend);

    public List<User> showFriends(long idUser);

    public List<User> showCommonFriends(long userId, long otherId);

    public List<Long> getIds();
}
