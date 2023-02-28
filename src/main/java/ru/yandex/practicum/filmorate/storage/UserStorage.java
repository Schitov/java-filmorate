package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UserStorage implements InMemoryUserStorage {

    HashMap<Integer, User> users = new HashMap<>();
    private int id = 0;

    public void clear() {
        users.clear();
    }

    @Override
    public User getUser(Long id) {
        return users.get(id.intValue());
    }

    @Override
    public Set<Long> addFriend(Long idUser, Long idFriend) {
        users.get(idUser.intValue()).addFriend(idFriend);
        users.get(idFriend.intValue()).addFriend(idUser);
        return users.get(idUser.intValue()).getFriends();
    }

    @Override
    public Set<Long> deleteFriend(Long idUser, Long idFriend) {
        users.get(idUser.intValue()).deleteFriend(idFriend);
        return users.get(idUser.intValue()).getFriends();
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
    public List<User> showFriends(Long idUser) {
        return users.get(idUser.intValue())
                .getFriends()
                .stream()
                .map(id -> users.get(id.intValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> showCommonFriends(Long userId, Long otherId) {
        List<User> commonFriends = new ArrayList<>();

        Set<Long> friendOfUser = users.get(userId.intValue()).getFriends();
        Set<Long> friendOfOtherUser = users.get(otherId.intValue()).getFriends();

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
        id = id + 1;
        return id;
    }

    @Override
    public HashMap<Integer, User> getUsers() {
        return users;
    }
}
