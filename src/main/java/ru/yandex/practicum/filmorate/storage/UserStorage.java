package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
public interface UserStorage {
    User addUser(User user);

    Collection<User> showUsers();

    User updateUser(User user);

    int generatorId();

    public User getUser(int id);

    public Set<Long> addFriend(int idUser, int idFriend);

    public Set<Long> deleteFriend(int idUser, int idFriend);

    public List<User> showFriends(int idUser);

    public List<User> showCommonFriends(int userId, int otherId);

    public HashMap<Integer, User> getUsers();
}
