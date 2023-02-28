package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
public interface InMemoryUserStorage {
    User addUser(User user);

    Collection<User> showUsers();

    User updateUser(User user);

    int generatorId();

    public User getUser(Long id);

    public Set<Long> addFriend(Long idUser, Long idFriend);

    public Set<Long> deleteFriend(Long idUser, Long idFriend);

    public List<User> showFriends(Long idUser);

    public List<User> showCommonFriends(Long userId, Long otherId);

    public HashMap<Integer, User> getUsers();
    public void clear();
}
