package ru.yandex.practicum.filmorate.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    HashMap<Integer, User> users = new HashMap<>();
    private int id = 0;

    @PostMapping()
    public User addUser(@Valid @RequestBody User user) {
        log.info("add user {}", user.toString());
        checkAndFillUserName(user);
        checkSpaces(user);
        checkBirthday(user);
        user.setId(generatorId());
        users.put(user.getId(), user);
        return user;
    }

    @GetMapping()
    public Collection<User> showUsers() {
        log.info("show films {}", users.values().toString());
        return users.values();
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("update user {}", user);
        checkUserInUsers(user);
        checkAndFillUserName(user);
        checkSpaces(user);
        checkBirthday(user);
        users.replace(user.getId(), user);
        return user;
    }

    private void checkAndFillUserName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    private void checkUserInUsers(User user) {
        if (!users.containsKey(user.getId())) {
            throw new ValidException("User with number is not existed");
        }
    }

    private void checkSpaces(User user) {
        if (user.getLogin().contains(" ")) {
            throw new ValidException("Login musn't contain spaces");
        }
    }

    private void checkBirthday(User user) {
        if(!user.getBirthday().isBefore(LocalDate.now())) {
            throw new ValidException("This birthday is not existed");
        }
    }

    private int generatorId() {
        id = id + 1;
        return id;
    }
}
