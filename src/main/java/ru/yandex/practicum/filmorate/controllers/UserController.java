package ru.yandex.practicum.filmorate.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.ModelValidator;

import java.util.Collection;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private ModelValidator userValidator;
    HashMap<Integer, User> users = new HashMap<>();
    private int id = 0;

    @PostMapping()
    public User addUser(@Valid @RequestBody User user) {
        log.info("add user {}", user.toString());
        Errors errors = new BeanPropertyBindingResult(user, "film");
        userValidator.validate(user, errors);
        user.setId(generatorId());
        user.setName(user.getName() == null || user.getName().isBlank() ? user.getLogin() : user.getName());
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
        Errors errors = new BeanPropertyBindingResult(user, "film");
        userValidator.validate(user, errors);
        userValidator.presentUserValidate(user, users);
        user.setName(user.getName() == null || user.getName().isBlank() ? user.getLogin() : user.getName());
        users.replace(user.getId(), user);
        return user;
    }

    private int generatorId() {
        id = id + 1;
        return id;
    }
}
