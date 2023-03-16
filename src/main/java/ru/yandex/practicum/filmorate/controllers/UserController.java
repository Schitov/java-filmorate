package ru.yandex.practicum.filmorate.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public User addUser(@Valid @RequestBody User user) {
        userService.addUser(user);
        return user;
    }

    @GetMapping()
    public Collection<User> showUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") long userId) {
        return userService.getUser(userId);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        userService.updateUser(user);
        return user;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public Set<Long> addFriend(@PathVariable("id") long userId,
                               @PathVariable("friendId") long friendId) {
        return userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public Set<Long> deleteFriend(@PathVariable("id") long userId,
                                  @PathVariable("friendId") long friendId) {
        return userService.deleteFriend(userId, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable("id") long userId) {
        return userService.getFriends(userId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable("id") long userId,
                                       @PathVariable("otherId") long otherId) {
        return userService.showCommonFriends(userId, otherId);
    }
}
