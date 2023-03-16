package ru.yandex.practicum.filmorate.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UserDBStorage implements UserStorage {

    HashMap<Long, User> users = new HashMap<>();
    private long id = 0;

    @Override
    public User getUser(long id) {
        return users.get(id);
    }

    @Override
    public Set<Long> addFriend(long idUser, long idFriend) {
        users.get(idUser).addFriend(idFriend);
        users.get(idFriend).addFriend(idUser);
        return users.get(idUser).getFriends();
    }

    @Override
    public Set<Long> deleteFriend(long idUser, long idFriend) {
        users.get(idUser).deleteFriend(idFriend);
        return users.get(idUser).getFriends();
    }

    @Override
    public User addUser(User user) {
        user.setId(generatorId());
        user.setName(user.getName() == null || user.getName().isBlank() ? user.getLogin() : user.getName());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        user.setName(user.getName() == null || user.getName().isBlank() ? user.getLogin() : user.getName());
        users.replace(user.getId(), user);
        return user;
    }

    @Override
    public Collection<User> showUsers() {
        return users.values();
    }

    @Override
    public List<User> showFriends(long idUser) {
        return users.get(idUser)
                .getFriends()
                .stream()
                .map(id -> users.get(id))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> showCommonFriends(long userId, long otherId) {
        List<User> commonFriends = new ArrayList<>();

        Set<Long> friendOfUser = users.get(userId).getFriends();
        Set<Long> friendOfOtherUser = users.get(otherId).getFriends();

        log.info(friendOfUser.toString());
        log.info(friendOfOtherUser.toString());

        int countFriendsOfUser = friendOfUser.size();
        if (countFriendsOfUser == 0) {
            return new ArrayList<>();
        }

        int countFriendsOfOtherUser = friendOfOtherUser.size();

        if (countFriendsOfUser < countFriendsOfOtherUser) {
            log.info(String.valueOf(countFriendsOfUser));
            log.info(friendOfUser.toString());
            log.info(String.valueOf(countFriendsOfOtherUser));
            log.info(friendOfOtherUser.toString());
            for (Long id : friendOfUser) {
                if (friendOfOtherUser.contains(id)) {
                    commonFriends.add(users.get(id));
                }
            }
        } else {
            for (Long id : friendOfOtherUser) {
                if (friendOfUser.contains(id)) {
                    commonFriends.add(users.get(id));
                }
            }
        }
        return commonFriends;
    }

    @Override
    public int generatorId() {
        return (int) ++id;
    }

    @Override
    public HashMap<Long, User> getUsers() {
        return users;
    }
}
