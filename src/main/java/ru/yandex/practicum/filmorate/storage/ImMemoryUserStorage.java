package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ImMemoryUserStorage implements UserStorage {

    HashMap<Integer, User> users = new HashMap<>();
    private int id = 0;

    @Override
    public User getUser(int id) {
        return users.get(id);
    }

    @Override
    public Set<Long> addFriend(int idUser, int idFriend) {
        users.get(idUser).addFriend(idFriend);
        users.get(idFriend).addFriend(idUser);
        return users.get(idUser).getFriends();
    }

    @Override
    public Set<Long> deleteFriend(int idUser, int idFriend) {
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
    public List<User> showFriends(int idUser) {
        return users.get(idUser)
                .getFriends()
                .stream()
                .map(id -> users.get(id.intValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> showCommonFriends(int userId, int otherId) {
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
                    commonFriends.add(users.get(id.intValue()));
                }
            }
        } else {
            for (Long id : friendOfOtherUser) {
                if (friendOfUser.contains(id)) {
                    commonFriends.add(users.get(id.intValue()));
                }
            }
        }
        return commonFriends;
    }

    @Override
    public int generatorId() {
        return ++id;
    }

    @Override
    public HashMap<Integer, User> getUsers() {
        return users;
    }
}
