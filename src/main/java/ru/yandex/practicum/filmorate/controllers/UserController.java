package ru.yandex.practicum.filmorate.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public User addUser(@Valid @RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping()
    public Collection<User> showUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") long userId) {
        log.info(String.valueOf(userId));
        return userService.getUser(userId);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info(String.valueOf(user));
        userService.updateUser(user);
        return user;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public int addFriend(@PathVariable("id") long userId,
                         @PathVariable("friendId") long friendId) {
        log.info("User id: " + String.valueOf(userId));
        log.info("Friend id: " + String.valueOf(friendId));
        return userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public int deleteFriend(@PathVariable("id") long userId,
                            @PathVariable("friendId") long friendId) {
        log.info("User id: " + String.valueOf(userId));
        log.info("Friend id: " + String.valueOf(friendId));
        return userService.deleteFriend(userId, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable("id") long userId) {
        log.info("User id: " + String.valueOf(userId));
        return userService.getFriends(userId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable("id") long userId,
                                       @PathVariable("otherId") long otherId) {
        log.info("User1 id: " + String.valueOf(userId));
        log.info("User2 id: " + String.valueOf(otherId));
        return userService.showCommonFriends(userId, otherId);
    }
}
