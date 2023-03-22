package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import ru.yandex.practicum.filmorate.dao.impl.UserDBStorage;
import ru.yandex.practicum.filmorate.exceptions.ExistenceOfObjectException;
import ru.yandex.practicum.filmorate.exceptions.ValidException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.dao.UserStorage;
import ru.yandex.practicum.filmorate.validators.ModelValidator;

import java.util.Collection;
import java.util.List;

@Service
public class UserService {

    UserStorage inMemoryUserStorage;
    @Autowired
    private ModelValidator userValidator;

    @Autowired
    public UserService(UserDBStorage userStorage) {
        this.inMemoryUserStorage = userStorage;
    }

    public Collection<User> getUsers() {
        return inMemoryUserStorage.showUsers();
    }

    public User getUser(long id) {
        if (checkPresenceAndPositiveOfValue(id)) {
            userValidator.objectPresenceValidate(id, inMemoryUserStorage.getIds());
            return inMemoryUserStorage.getUser(id);
        }
        throw new ValidException("Id of user must be more than 0");
    }

    public User addUser(User user) {
        Errors errors = new BeanPropertyBindingResult(user, "film");
        userValidator.validate(user, errors);
        user.setName(user.getName() == null || user.getName().isBlank() ? user.getLogin() : user.getName());
        return inMemoryUserStorage.addUser(user);
    }

    public void updateUser(User user) {
        Errors errors = new BeanPropertyBindingResult(user, "film");
        userValidator.validate(user, errors);
        userValidator.objectPresenceValidate(user.getId(), inMemoryUserStorage.getIds());
        inMemoryUserStorage.updateUser(user);
    }

    public int addFriend(long idUser, long idFriend) {
        if (checkPresenceAndPositiveOfValues(idUser, idFriend)) {
            return inMemoryUserStorage.addFriend(idUser, idFriend);
        }
        throw new ExistenceOfObjectException("idFilm or idUser must be more than 0");
    }

    public int deleteFriend(long idUser, long idFriend) {
        if (checkPresenceAndPositiveOfValues(idUser, idFriend)) {
            return inMemoryUserStorage.deleteFriend(idUser, idFriend);
        }
        throw new ValidException("idFilm or idUser must be more than 0");
    }

    public List<User> getFriends(long userId) {
        if (checkPresenceAndPositiveOfValue(userId)) {
            return inMemoryUserStorage.showFriends(userId);
        }
        throw new ValidException("idUser must be more than 0");
    }

    public List<User> showCommonFriends(long idUser, long otherId) {
        if (checkPresenceAndPositiveOfValues(idUser, otherId)) {
            return inMemoryUserStorage.showCommonFriends(idUser, otherId);
        }
        throw new ValidException("id of user must be more than 0");
    }

    public boolean checkPresenceAndPositiveOfValues(long idUser, long idFilm) {
        return idUser >= 0 & idFilm >= 0;
    }

    public boolean checkPresenceAndPositiveOfValue(long id) {
        return id >= 0;
    }
}
